package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.exception.ResourceNotFoundException;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.util.CandidateMapper;
import com.nagarro.si.cm.util.CandidateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
    private CandidateValidator candidateValidator;

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
        candidate1.setEmail("diana.hategan1107@gmail.com");
        candidate1.setPhoneNumber("0760271177");

        candidateDto1 = new CandidateDto();
        candidateDto1.setUsername("DianaH");
        candidateDto1.setEmail("diana.hategan1107@gmail.com");
        candidateDto1.setPhoneNumber("0760271177");

        candidate2 = new Candidate();
        candidate2.setUsername("David29");
        candidate2.setEmail("david29@gmail.com");
        candidate2.setPhoneNumber("0751066222");

        candidateDto2 = new CandidateDto();
        candidateDto2.setUsername("David29");
        candidateDto2.setEmail("david29@gmail.com");
        candidateDto2.setPhoneNumber("0751066222");
    }

    @Test
    public void testSaveCandidate() {
        Candidate savedCandidate = candidate1;
        CandidateDto savedCandidateDto = candidateDto1;
        when(candidateMapper.toCandidate(candidateDto1)).thenReturn(candidate1);
        when(candidateRepository.save(candidate1)).thenReturn(savedCandidate);
        when(candidateMapper.toDTO(savedCandidate)).thenReturn(savedCandidateDto);

        CandidateDto result = candidateService.saveCandidate(candidateDto1);

        assertNotNull(result);
        assertEquals(savedCandidateDto, result);
        verify(candidateMapper, times(1)).toCandidate(candidateDto1);
        verify(candidateRepository, times(1)).save(candidate1);
        verify(candidateMapper, times(1)).toDTO(savedCandidate);
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
        verify(candidateMapper, times(1)).toDTO(candidate1);
        verify(candidateMapper, times(1)).toDTO(candidate2);
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

    @Test
    public void testGetCandidateByUsername() {
        String username = "DianaH";

        when(candidateRepository.getCandidateByUsername(username)).thenReturn(Optional.of(candidate1));
        when(candidateMapper.toDTO(candidate1)).thenReturn(candidateDto1);

        CandidateDto result = candidateService.getCandidateByUsername(username);

        assertNotNull(result);
        assertEquals(candidateDto1, result);
        verify(candidateRepository, times(1)).getCandidateByUsername(username);
        verify(candidateMapper, times(1)).toDTO(candidate1);
    }

    @Test
    public void testGetCandidateByUsernameNotFound() {
        String username = "Dia";
        when(candidateRepository.getCandidateByUsername(username)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            candidateService.getCandidateByUsername(username);
        });

        assertEquals(String.format("Candidate with username = %s not found", username), thrown.getMessage());
        verify(candidateRepository, times(1)).getCandidateByUsername(username);
        verify(candidateMapper, never()).toDTO(any());
    }

    @Test
    public void testFilterCandidatesByAnyField() {
        List<Candidate> candidates = Collections.singletonList(candidate1);
        Map<String, Object> filters = new HashMap<>();
        filters.put("name", "Diana");
        filters.put("email", "diana");
        when(candidateRepository.findAll(any(Specification.class))).thenReturn(candidates);
        when(candidateMapper.toDTO(candidate1)).thenReturn(candidateDto1);

        List<CandidateDto> result = candidateService.filterCandidatesByAnyField(filters);

        assertEquals(1, result.size());
        assertEquals("DianaH", result.get(0).getUsername());
        assertEquals("diana.hategan1107@gmail.com", result.get(0).getEmail());
        verify(candidateRepository, times(1)).findAll(any(Specification.class));
    }

    @Test
    public void testFilterCandidatesByAnyFieldNoMatching() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("name", "DianaHategan");
        filters.put("email", "diana.hategan1107@yahoo.com");
        when(candidateRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            candidateService.filterCandidatesByAnyField(filters);
        });

        assertEquals("No candidates were found matching the provided filters", thrown.getMessage());
        verify(candidateRepository, times(1)).findAll(any(Specification.class));
        verify(candidateMapper, never()).toDTO(any(Candidate.class));
    }

    @Test
    public void testDeleteCandidateById() {
        int candidateId = 1;
        when(candidateRepository.existsCandidateById(candidateId)).thenReturn(true);

        candidateService.deleteCandidateById(candidateId);

        verify(candidateRepository, times(1)).deleteById(candidateId);
    }

    @Test
    public void testDeleteCandidateByIdNotFound() {
        int candidateId = 1;
        when(candidateRepository.existsCandidateById(candidateId)).thenReturn(false);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            candidateService.deleteCandidateById(candidateId);
        });

        assertEquals("candidate with id [1] not found", thrown.getMessage());
        verify(candidateRepository, times(1)).existsCandidateById(candidateId);
        verify(candidateRepository, never()).deleteById(candidateId);
    }

    @Test
    public void testUpdateCandidate() throws ParseException {
        int candidateId = 1;
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate1));
        doNothing().when(candidateValidator).updateCandidateFields(candidate1, candidateDto1);

        candidateService.updateCandidate(candidateId, candidateDto1);

        verify(candidateRepository, times(1)).findById(candidateId);
        verify(candidateValidator, times(1)).updateCandidateFields(candidate1, candidateDto1);
        verify(candidateRepository, times(1)).save(candidate1);
    }

    @Test
    public void testUpdateCandidateNotFound() {
        int candidateId = 1;
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            candidateService.updateCandidate(candidateId, candidateDto1);
        });

        assertEquals("Candidate with id [1] not found", thrown.getMessage());
        verify(candidateRepository, times(1)).findById(candidateId);
        verify(candidateRepository, never()).save(any(Candidate.class));
    }

    @Test
    public void testPatchCandidate() throws ParseException {
        int candidateId = 1;
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate1));
        doNothing().when(candidateValidator).updateCandidateFields(candidate1, candidateDto1);

        candidateService.patchCandidate(candidateId, candidateDto1);

        verify(candidateRepository, times(1)).findById(candidateId);
        verify(candidateValidator, times(1)).updateCandidateFields(candidate1, candidateDto1);
        verify(candidateRepository, times(1)).save(candidate1);
    }

    @Test
    public void testPatchCandidateNotFound() {
        int candidateId = 1;
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            candidateService.patchCandidate(candidateId, candidateDto1);
        });

        assertEquals("Candidate with id [1] not found", thrown.getMessage());
        verify(candidateRepository, times(1)).findById(candidateId);
        verify(candidateRepository, never()).save(any(Candidate.class));
    }
}
