package com.nagarro.si.um.service;

import com.nagarro.si.um.dto.InterviewDTO;
import com.nagarro.si.um.entity.Interview;
import com.nagarro.si.um.entity.InterviewType;
import com.nagarro.si.um.entity.User;
import com.nagarro.si.um.exception.EntityNotFoundException;
import com.nagarro.si.um.mapper.InterviewMapper;
import com.nagarro.si.um.repository.InterviewRepository;
import com.nagarro.si.um.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class InterviewService {
    private InterviewRepository interviewRepository;
    private UserRepository userRepository;
    private InterviewMapper interviewMapper;

    @Autowired
    public InterviewService(InterviewRepository interviewRepository, UserRepository userRepository, InterviewMapper interviewMapper) {
        this.interviewRepository = interviewRepository;
        this.userRepository = userRepository;
        this.interviewMapper = interviewMapper;
    }

    public InterviewDTO scheduleInterview(InterviewDTO interviewDTO) {
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

        Set<User> users = new HashSet<>();
        for (String email : emails) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("User with email = %s not found", email)));
            users.add(user);
        }
        return users;
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
        Map<String, Integer> roleCounts = new HashMap<>();
        for (User user : users) {
            String roleName = user.getRole().getRoleName();
            roleCounts.put(roleName, roleCounts.getOrDefault(roleName, 0) + 1);
        }
        return roleCounts;
    }

    private void validateAttendeesAlreadyBooked(Set<User> users, Timestamp startDate, Timestamp endDate) {
        for (User user : users) {
            List<Interview> overlappingInterviews = interviewRepository.findOverlappingInterviews(user.getUserId(), startDate, endDate);
            if (!overlappingInterviews.isEmpty()) {
                throw new IllegalArgumentException(String.format("User with email = %s is already booked for an interview", user.getEmail()));
            }
        }
    }
}
