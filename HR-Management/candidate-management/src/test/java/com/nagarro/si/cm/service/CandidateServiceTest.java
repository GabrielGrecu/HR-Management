package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.exception.ResourceNotFoundException;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.util.CandidateMapper;
import com.nagarro.si.cm.util.ValidatorUtil;
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
import static org.mockito.Mockito.doNothing;
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

    @Mock
    private ValidatorUtil validatorUtil;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    @Test
    public void testSaveCandidate() {
        Candidate candidate = new Candidate();
        CandidateDto candidateDto = new CandidateDto();
        Candidate savedCandidate = new Candidate();
        CandidateDto savedCandidateDto = new CandidateDto();

        doNothing().when(validatorUtil).validate(candidateDto);
        when(candidateMapper.toCandidate(candidateDto)).thenReturn(candidate);
        when(candidateRepository.save(candidate)).thenReturn(savedCandidate);
        when(candidateMapper.toDTO(savedCandidate)).thenReturn(savedCandidateDto);

        CandidateDto result = candidateService.saveCandidate(candidateDto);

        assertNotNull(result);
        assertEquals(savedCandidateDto, result);
        verify(validatorUtil, times(1)).validate(candidateDto);
        verify(candidateMapper, times(1)).toCandidate(candidateDto);
        verify(candidateRepository, times(1)).save(candidate);
        verify(candidateMapper, times(1)).toDTO(savedCandidate);
    }

    @Test
    public void testSaveCandidateInvalidEmail() {
        CandidateDto candidateDto = new CandidateDto();
        doThrow(new IllegalArgumentException("Invalid email format")).when(validatorUtil).validate(candidateDto);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            candidateService.saveCandidate(candidateDto);
        });

        assertEquals("Invalid email format", exception.getMessage());
        verify(validatorUtil, times(1)).validate(candidateDto);
        verifyNoInteractions(candidateRepository, candidateMapper);
    }

    @Test
    public void testSaveCandidateInvalidPhoneNumber() {
        CandidateDto candidateDto = new CandidateDto();
        doThrow(new IllegalArgumentException("Invalid phone number format")).when(validatorUtil).validate(candidateDto);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            candidateService.saveCandidate(candidateDto);
        });

        assertEquals("Invalid phone number format", exception.getMessage());
        verify(validatorUtil, times(1)).validate(candidateDto);
        verifyNoInteractions(candidateRepository, candidateMapper);
    }

    @Test
    public void testGetAllCandidates() {
        Candidate candidate1 = new Candidate();
        Candidate candidate2 = new Candidate();
        CandidateDto candidateDto1 = new CandidateDto();
        CandidateDto candidateDto2 = new CandidateDto();

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
        Candidate candidate = new Candidate();
        CandidateDto candidateDto = new CandidateDto();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(candidateMapper.toDTO(candidate)).thenReturn(candidateDto);

        CandidateDto result = candidateService.getCandidateById(candidateId);

        assertNotNull(result);
        assertEquals(candidateDto, result);
        verify(candidateRepository, times(1)).findById(candidateId);
        verify(candidateMapper, times(1)).toDTO(candidate);
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
