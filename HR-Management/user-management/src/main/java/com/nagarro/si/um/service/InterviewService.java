package com.nagarro.si.um.service;

import com.nagarro.si.common.dto.CandidateDto;
import com.nagarro.si.um.dto.InterviewDTO;
import com.nagarro.si.um.entity.Interview;
import com.nagarro.si.um.entity.InterviewType;
import com.nagarro.si.um.entity.User;
import com.nagarro.si.um.exception.EntityNotFoundException;
import com.nagarro.si.um.mapper.InterviewMapper;
import com.nagarro.si.um.repository.InterviewRepository;
import com.nagarro.si.um.repository.UserRepository;
import com.nagarro.si.um.util.CandidateServiceClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;
    private final InterviewMapper interviewMapper;
    private final CandidateServiceClientUtil candidateServiceClientUtil;

    @Autowired
    public InterviewService(InterviewRepository interviewRepository,
                            UserRepository userRepository,
                            InterviewMapper interviewMapper,
                            CandidateServiceClientUtil candidateServiceClientUtil) {
        this.interviewRepository = interviewRepository;
        this.userRepository = userRepository;
        this.interviewMapper = interviewMapper;
        this.candidateServiceClientUtil = candidateServiceClientUtil;
    }

    public InterviewDTO scheduleInterview(InterviewDTO interviewDTO) {
        CandidateDto candidateDto = candidateServiceClientUtil.getCandidateByEmail(interviewDTO.candidateEmail())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Candidate with email = %s not found", interviewDTO.candidateEmail())));

        log.info("Retrieved candidate: {}", candidateDto);

        Set<User> attendees = findUsersByEmails(interviewDTO.attendeesEmails());

        validateDates(interviewDTO.startDate(), interviewDTO.endDate());
        validateAttendeesNumber(attendees, interviewDTO.interviewType());
        validateAttendeesAlreadyBooked(attendees, interviewDTO.startDate(), interviewDTO.endDate());

        Interview interview = interviewMapper.toInterview(interviewDTO);
        interview.setUsers(attendees);
        Interview savedInterview = interviewRepository.save(interview);

        return interviewMapper.toInterviewDTO(savedInterview);
    }

    private Set<User> findUsersByEmails(List<String> emails) {
        if (Objects.isNull(emails) || emails.isEmpty()) {
            throw new IllegalArgumentException("Email list must not be null or empty");
        }

        return emails.stream()
                .map(email -> userRepository.findByEmail(email)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("User with email = %s not found", email))))
                .collect(Collectors.toSet());
    }

    private void validateDates(Timestamp startDate, Timestamp endDate) {
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    private void validateAttendeesNumber(Set<User> users, InterviewType interviewType) {
        Map<String, Integer> attendeesNumber = countAttendeesByRole(users);
        int hrNumber = attendeesNumber.getOrDefault("HR", 0);
        int techNumber = attendeesNumber.getOrDefault("Tech", 0);
        int pteNumber = attendeesNumber.getOrDefault("PTE", 0);

        if (pteNumber != 1) {
            throw new IllegalArgumentException("All interviews require a PTE user");
        }
        if (interviewType == InterviewType.HR) {
            if (hrNumber < 2) {
                throw new IllegalArgumentException("HR interviews require at least 2 HR users");
            }
        } else if (interviewType == InterviewType.TECH) {
            if (techNumber < 2) {
                throw new IllegalArgumentException("Technical interviews require at least 2 Tech users");
            }
        }
    }

    private Map<String, Integer> countAttendeesByRole(Set<User> users) {
        return users.stream()
                .collect(Collectors.groupingBy(
                        user -> user.getRole().getRoleName(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
    }

    private void validateAttendeesAlreadyBooked(Set<User> users, Timestamp startDate, Timestamp endDate) {
        users.stream()
                .filter(user -> !interviewRepository.findOverlappingInterviews(user.getUserId(), startDate, endDate).isEmpty())
                .findAny()
                .ifPresent(user -> {
                    throw new IllegalArgumentException(String.format("User with email = %s is already booked for an interview", user.getEmail()));
                });
    }
}
