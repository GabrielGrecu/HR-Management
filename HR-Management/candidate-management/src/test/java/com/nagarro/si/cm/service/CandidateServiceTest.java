package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.exception.ResourceNotFoundException;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.util.CandidateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private CandidateMapper candidateMapper;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    private Candidate candidate1;
    private Candidate candidate2;
    private CandidateDto candidateDto1;
    private CandidateDto candidateDto2;

    @BeforeEach
    public void setup() {
        candidate1 = new Candidate();
        candidate1.setUsername("DianaH");
        candidate1.setEmail("dia@gmail.com");
        candidate1.setPhoneNumber("0760271177");

        candidateDto1 = new CandidateDto();
        candidateDto1.setUsername("DianaH");
        candidateDto1.setEmail("dia@gmail.com");
        candidateDto1.setPhoneNumber("0760271177");

        candidate2 = new Candidate();
        candidate2.setUsername("DianaH");
        candidate2.setEmail("dia@gmail.com");
        candidate2.setPhoneNumber("0760271177");

        candidateDto2 = new CandidateDto();
        candidateDto2.setUsername("DianaH");
        candidateDto2.setEmail("dia@gmail.com");
        candidateDto2.setPhoneNumber("0760271177");
    }

    @Test
    public void testSaveCandidate() {
        when(candidateMapper.toCandidate(candidateDto1)).thenReturn(candidate1);
        when(candidateRepository.save(candidate1)).thenReturn(candidate2);
        when(candidateMapper.toDTO(candidate2)).thenReturn(candidateDto2);

        CandidateDto result = candidateService.saveCandidate(candidateDto1);

        assertNotNull(result);
        assertEquals(candidateDto2, result);
        verify(candidateMapper, times(1)).toCandidate(candidateDto1);
        verify(candidateRepository, times(1)).save(candidate1);
        verify(candidateMapper, times(1)).toDTO(candidate2);
    }

    @Test
    public void testSaveCandidateInvalidEmail() {
        candidateDto1.setEmail("diagmail.com");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            candidateService.saveCandidate(candidateDto1);
        });

        assertEquals("Invalid email format", exception.getMessage());
        verifyNoInteractions(candidateRepository, candidateMapper);
    }

    @Test
    public void testSaveCandidateInvalidPhoneNumber() {
        candidateDto1.setPhoneNumber("760271177");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            candidateService.saveCandidate(candidateDto1);
        });

        assertEquals("Invalid phone number format", exception.getMessage());
        verifyNoInteractions(candidateRepository, candidateMapper);
    }

    @Test
    public void testGetAllCandidates() {
        when(candidateRepository.findAll()).thenReturn(Arrays.asList(candidate1, candidate2));
        when(candidateMapper.toDTO(candidate1)).thenReturn(candidateDto1);
        when(candidateMapper.toDTO(candidate2)).thenReturn(candidateDto2);

        List<CandidateDto> result = candidateService.getAllCandidates();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(candidateDto1, result.get(0));
        assertEquals(candidateDto2, result.get(1));
        verify(candidateRepository, times(1)).findAll();
        verify(candidateMapper, times(2)).toDTO(candidate1);
        verify(candidateMapper, times(2)).toDTO(candidate2);
    }

    @Test
    public void testGetCandidateById() {
        int candidateId = 1;

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate1));
        when(candidateMapper.toDTO(candidate1)).thenReturn(candidateDto1);

        CandidateDto result = candidateService.getCandidateById(candidateId);

        assertNotNull(result);
        assertEquals(candidateDto1, result);
        verify(candidateRepository, times(1)).findById(candidateId);
        verify(candidateMapper, times(1)).toDTO(candidate1);
    }

    @Test
    public void testGetCandidateByIdNotFound() {
        int candidateId = 1;
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            candidateService.getCandidateById(candidateId);
        });

        assertEquals(String.format("Candidate with id = %d not found", candidateId), thrown.getMessage());
        verify(candidateRepository, times(1)).findById(candidateId);
        verify(candidateMapper, never()).toDTO(any());
    }
}
