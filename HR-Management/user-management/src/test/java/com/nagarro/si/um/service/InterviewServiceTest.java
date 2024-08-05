package com.nagarro.si.um.service;

import com.nagarro.si.common.dto.CandidateDto;
import com.nagarro.si.um.dto.InterviewDTO;
import com.nagarro.si.um.entity.Interview;
import com.nagarro.si.um.entity.InterviewType;
import com.nagarro.si.um.entity.Role;
import com.nagarro.si.um.entity.User;
import com.nagarro.si.um.exception.EntityNotFoundException;
import com.nagarro.si.um.mapper.InterviewMapper;
import com.nagarro.si.um.repository.InterviewRepository;
import com.nagarro.si.um.repository.UserRepository;
import com.nagarro.si.um.util.CandidateServiceClientUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InterviewServiceTest {

    @Mock
    private InterviewRepository interviewRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private InterviewMapper interviewMapper;
    @Mock
    private CandidateServiceClientUtil candidateServiceClientUtil;
    @InjectMocks
    private InterviewService interviewService;
    private User pteUser;
    private User hrUser1;
    private User hrUser2;
    private InterviewDTO interviewDTO;
    private Interview interview;
    private CandidateDto candidateDto;

    @BeforeEach
    public void setup() {
        Role pteRole = new Role();
        pteRole.setRoleName("PTE");
        Role hrRole = new Role();
        hrRole.setRoleName("HR");

        pteUser = new User();
        pteUser.setUserId(1L);
        pteUser.setEmail("pte@gmail.com");
        pteUser.setRole(pteRole);

        hrUser1 = new User();
        hrUser1.setUserId(2L);
        hrUser1.setEmail("hr1@yahoo.com");
        hrUser1.setRole(hrRole);

        hrUser2 = new User();
        hrUser2.setUserId(3L);
        hrUser2.setEmail("hr2@yahoo.com");
        hrUser2.setRole(hrRole);

        interviewDTO = new InterviewDTO(
                Arrays.asList("pte@gmail.com", "hr1@yahoo.com", "hr2@yahoo.com"),
                "candidate@gmail.com",
                Timestamp.valueOf("2024-07-26 14:00:00"),
                Timestamp.valueOf("2024-07-26 14:30:00"),
                InterviewType.HR,
                "Interview for software dev position",
                "HR interview"
        );

        interview = new Interview();
        candidateDto = new CandidateDto();
    }

    @Test
    public void testScheduleInterviewSuccess() {
        when(candidateServiceClientUtil.getCandidateByEmail("candidate@gmail.com")).thenReturn(Optional.of(candidateDto));
        when(userRepository.findByEmail("pte@gmail.com")).thenReturn(Optional.of(pteUser));
        when(userRepository.findByEmail("hr1@yahoo.com")).thenReturn(Optional.of(hrUser1));
        when(userRepository.findByEmail("hr2@yahoo.com")).thenReturn(Optional.of(hrUser2));
        when(interviewMapper.toInterview(interviewDTO)).thenReturn(interview);
        when(interviewMapper.toInterviewDTO(any(Interview.class))).thenReturn(interviewDTO);
        when(interviewRepository.save(any(Interview.class))).thenReturn(interview);

        InterviewDTO result = interviewService.scheduleInterview(interviewDTO);

        assertNotNull(result);
        assertEquals(result, interviewDTO);
        verify(interviewRepository, times(1)).save(any(Interview.class));
        verify(userRepository, times(1)).findByEmail("pte@gmail.com");
        verify(userRepository, times(1)).findByEmail("hr1@yahoo.com");
        verify(userRepository, times(1)).findByEmail("hr2@yahoo.com");
    }

    @Test
    public void testScheduleInterviewCandidateNotFound() {
        when(candidateServiceClientUtil.getCandidateByEmail("candidate@gmail.com")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                interviewService.scheduleInterview(interviewDTO));

        assertEquals("Candidate with email = candidate@gmail.com not found", exception.getMessage());
    }

    @Test
    public void testScheduleInterviewNullListOfEmails() {
        when(candidateServiceClientUtil.getCandidateByEmail("candidate@gmail.com")).thenReturn(Optional.of(candidateDto));
        InterviewDTO nullEmailListInterviewDTO = new InterviewDTO(
                null,
                "candidate@gmail.com",
                Timestamp.valueOf("2024-07-26 14:00:00"),
                Timestamp.valueOf("2024-07-26 14:30:00"),
                InterviewType.HR,
                "Interview for software dev position",
                "HR interview"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                interviewService.scheduleInterview(nullEmailListInterviewDTO));

        assertEquals("Email list must not be null or empty", exception.getMessage());
        verifyNoInteractions(userRepository, interviewRepository, interviewMapper);
    }

    @Test
    public void testScheduleInterviewUserNotFound() {
        when(candidateServiceClientUtil.getCandidateByEmail("candidate@gmail.com")).thenReturn(Optional.of(candidateDto));
        when(userRepository.findByEmail("pte@gmail.com")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                interviewService.scheduleInterview(interviewDTO));

        assertEquals("User with email = pte@gmail.com not found", exception.getMessage());
    }

    @Test
    public void testScheduleInterviewInvalidDates() {
        when(candidateServiceClientUtil.getCandidateByEmail("candidate@gmail.com")).thenReturn(Optional.of(candidateDto));
        when(userRepository.findByEmail("pte@gmail.com")).thenReturn(Optional.of(pteUser));
        when(userRepository.findByEmail("hr1@yahoo.com")).thenReturn(Optional.of(hrUser1));
        when(userRepository.findByEmail("hr2@yahoo.com")).thenReturn(Optional.of(hrUser2));

        InterviewDTO invalidDateInterviewDTO = new InterviewDTO(
                Arrays.asList("pte@gmail.com", "hr1@yahoo.com", "hr2@yahoo.com"),
                "candidate@gmail.com",
                Timestamp.valueOf("2024-07-30 11:00:00"),
                Timestamp.valueOf("2024-07-30 10:00:00"),
                InterviewType.HR,
                "Interview for software dev position",
                "HR interview"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                interviewService.scheduleInterview(invalidDateInterviewDTO));

        assertEquals("End date must be after start date", exception.getMessage());
        verifyNoInteractions(interviewRepository, interviewMapper);
    }

    @Test
    public void testScheduleInterviewInvalidPTEAttendees() {
        when(candidateServiceClientUtil.getCandidateByEmail("candidate@gmail.com")).thenReturn(Optional.of(candidateDto));
        when(userRepository.findByEmail("hr1@yahoo.com")).thenReturn(Optional.of(hrUser1));
        when(userRepository.findByEmail("hr2@yahoo.com")).thenReturn(Optional.of(hrUser2));

        InterviewDTO invalidPTEInterviewDTO = new InterviewDTO(
                Arrays.asList("hr1@yahoo.com", "hr2@yahoo.com"),
                "candidate@gmail.com",
                Timestamp.valueOf("2024-07-26 14:00:00"),
                Timestamp.valueOf("2024-07-26 14:30:00"),
                InterviewType.HR,
                "Interview for software dev position",
                "HR interview"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                interviewService.scheduleInterview(invalidPTEInterviewDTO));

        assertEquals("All interviews require a PTE user", exception.getMessage());
        verifyNoInteractions(interviewRepository, interviewMapper);
    }

    @Test
    public void testScheduleInterviewInvalidHRAttendees() {
        when(candidateServiceClientUtil.getCandidateByEmail("candidate@gmail.com")).thenReturn(Optional.of(candidateDto));
        when(userRepository.findByEmail("pte@gmail.com")).thenReturn(Optional.of(pteUser));
        when(userRepository.findByEmail("hr1@yahoo.com")).thenReturn(Optional.of(hrUser1));

        InterviewDTO invalidHRInterviewDTO = new InterviewDTO(
                Arrays.asList("pte@gmail.com", "hr1@yahoo.com"),
                "candidate@gmail.com",
                Timestamp.valueOf("2024-07-26 14:00:00"),
                Timestamp.valueOf("2024-07-26 14:30:00"),
                InterviewType.HR,
                "Interview for software dev position",
                "HR interview"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                interviewService.scheduleInterview(invalidHRInterviewDTO));

        assertEquals("HR interviews require at least 2 HR users", exception.getMessage());
        verifyNoInteractions(interviewRepository, interviewMapper);
    }

    @Test
    public void testScheduleInterviewInvalidTECHAttendees() {
        when(candidateServiceClientUtil.getCandidateByEmail("candidate@gmail.com")).thenReturn(Optional.of(candidateDto));
        when(userRepository.findByEmail("pte@gmail.com")).thenReturn(Optional.of(pteUser));

        InterviewDTO invalidTechInterviewDTO = new InterviewDTO(
                List.of("pte@gmail.com"),
                "candidate@gmail.com",
                Timestamp.valueOf("2024-07-26 14:00:00"),
                Timestamp.valueOf("2024-07-26 14:30:00"),
                InterviewType.TECH,
                "Interview for software dev position",
                "HR interview"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                interviewService.scheduleInterview(invalidTechInterviewDTO));

        assertEquals("Technical interviews require at least 2 Tech users", exception.getMessage());
        verifyNoInteractions(interviewRepository, interviewMapper);
    }

    @Test
    public void testScheduleInterviewAttendeesAlreadyBooked() {
        when(candidateServiceClientUtil.getCandidateByEmail("candidate@gmail.com")).thenReturn(Optional.of(candidateDto));
        when(userRepository.findByEmail("pte@gmail.com")).thenReturn(Optional.of(pteUser));
        when(userRepository.findByEmail("hr1@yahoo.com")).thenReturn(Optional.of(hrUser1));
        when(userRepository.findByEmail("hr2@yahoo.com")).thenReturn(Optional.of(hrUser2));
        Timestamp startDate = Timestamp.valueOf("2024-07-26 14:00:00");
        Timestamp endDate = Timestamp.valueOf("2024-07-26 14:30:00");
        when(interviewRepository.findOverlappingInterviews(1L, startDate, endDate))
                .thenReturn(List.of(new Interview()));
        InterviewDTO interviewDTOWithOverlap = new InterviewDTO(
                Arrays.asList("pte@gmail.com", "hr1@yahoo.com", "hr2@yahoo.com"),
                "candidate@gmail.com",
                startDate,
                endDate,
                InterviewType.HR,
                "Interview for software dev position",
                "HR interview"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                interviewService.scheduleInterview(interviewDTOWithOverlap));

        assertEquals("User with email = pte@gmail.com is already booked for an interview", exception.getMessage());
        verifyNoInteractions(interviewMapper);
    }
}
