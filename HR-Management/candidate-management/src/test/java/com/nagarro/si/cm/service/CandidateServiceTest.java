package com.nagarro.si.cm.service;

import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.exception.EntityNotFoundException;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.util.CandidateMapper;
import com.nagarro.si.common.dto.CandidateDto;
import com.nagarro.si.common.dto.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private CandidateMapper candidateMapper;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    private Candidate candidate;
    private CandidateDto candidateDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        candidate = new Candidate();
        candidate.setId(1);
        candidate.setUsername("john_doe");
        candidate.setEmail("john.doe@example.com");
        candidate.setPhoneNumber("1234567890");
        candidate.setCandidateStatus(Status.IN_PROGRESS);

        candidate.setBirthday(Date.valueOf("1994-07-11"));
        candidateDto = new CandidateDto();
        candidateDto.setId(1);
        candidateDto.setUsername("john_doe");
        candidateDto.setEmail("john.doe@example.com");
        candidateDto.setPhoneNumber("1234567890");
        candidateDto.setBirthday(LocalDate.of(1994, 7, 11));
    }

    @Test
    void testSaveCandidate_Success() {
        when(candidateRepository.existsCandidateByUsername(candidateDto.getUsername())).thenReturn(false);
        when(candidateRepository.existsCandidateByEmail(candidateDto.getEmail())).thenReturn(false);
        when(candidateRepository.existsCandidateByPhoneNumber(candidateDto.getPhoneNumber())).thenReturn(false);
        when(candidateMapper.toCandidate(candidateDto)).thenReturn(candidate);
        when(candidateRepository.save(candidate)).thenReturn(candidate);
        when(candidateMapper.toDTO(candidate)).thenReturn(candidateDto);

        CandidateDto savedCandidate = candidateService.saveCandidate(candidateDto);

        assertNotNull(savedCandidate);
        assertEquals(candidateDto.getUsername(), savedCandidate.getUsername());
        verify(candidateRepository).save(candidate);
    }


    @Test
    void testGetAllCandidates() {
        when(candidateRepository.findAll()).thenReturn(List.of(candidate));
        when(candidateMapper.toDTO(candidate)).thenReturn(candidateDto);

        List<CandidateDto> candidates = candidateService.getAllCandidates();

        assertNotNull(candidates);
        assertEquals(1, candidates.size());
        assertEquals(candidateDto, candidates.get(0));
        verify(candidateRepository).findAll();
    }

    @Test
    void testGetCandidateById_Success() {
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        when(candidateMapper.toDTO(candidate)).thenReturn(candidateDto);

        CandidateDto foundCandidate = candidateService.getCandidateById(candidate.getId());

        assertNotNull(foundCandidate);
        assertEquals(candidateDto.getId(), foundCandidate.getId());
        verify(candidateRepository).findById(candidate.getId());
    }

    @Test
    void testGetCandidateById_NotFound() {
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> candidateService.getCandidateById(candidate.getId()));
    }

    @Test
    void testDeleteCandidateById_Success() {
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));

        candidateService.deleteCandidateById(candidate.getId());

        verify(candidateRepository).delete(candidate);
    }

    @Test
    void testDeleteCandidateById_NotFound() {
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> candidateService.deleteCandidateById(candidate.getId()));
    }

    @Test
    void testUpdateCandidate_Success() {
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        when(candidateRepository.existsCandidateByUsername(anyString())).thenReturn(false);
        when(candidateRepository.existsCandidateByEmail(anyString())).thenReturn(false);
        when(candidateRepository.existsCandidateByPhoneNumber(anyString())).thenReturn(false);
        doNothing().when(candidateMapper).updateCandidateFromDto(any(Candidate.class), any(CandidateDto.class));
        when(candidateRepository.save(candidate)).thenReturn(candidate);

        candidateService.updateCandidate(candidate.getId(), candidateDto);

        verify(candidateRepository).save(candidate);
    }

    @Test
    void testPatchCandidate_Success() {
        when(candidateRepository.findById(candidate.getId())).thenReturn(Optional.of(candidate));
        when(candidateRepository.existsCandidateByUsername(anyString())).thenReturn(false);
        when(candidateRepository.existsCandidateByEmail(anyString())).thenReturn(false);
        when(candidateRepository.existsCandidateByPhoneNumber(anyString())).thenReturn(false);
        when(candidateRepository.save(candidate)).thenReturn(candidate);

        candidateService.patchCandidate(candidate.getId(), candidateDto);

        verify(candidateRepository).save(candidate);
    }

    @Test
    void testFilterCandidatesByAnyField() {
        Map<String, Object> filters = Map.of("username", "john_doe");
        when(candidateRepository.findAll(any(Specification.class))).thenReturn(List.of(candidate));
        when(candidateMapper.toDTO(candidate)).thenReturn(candidateDto);

        List<CandidateDto> result = candidateService.filterCandidatesByAnyField(filters);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(candidateRepository).findAll(any(Specification.class));
    }

    @Test
    void testFilterCandidatesByAnyField_NotFound() {
        Map<String, Object> filters = Map.of("username", "nonexistent_user");
        when(candidateRepository.findAll(any(Specification.class))).thenReturn(List.of());

        assertThrows(EntityNotFoundException.class, () -> candidateService.filterCandidatesByAnyField(filters));
    }
}
