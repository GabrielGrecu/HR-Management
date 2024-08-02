package com.nagarro.si.cm.controller;

import com.nagarro.si.common.dto.CandidateDto;
import com.nagarro.si.cm.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CandidateControllerTest {

    @Mock
    CandidateService candidateService;

    @InjectMocks
    CandidateController candidateController;

    @Test
    public void testCreateCandidate() {
        CandidateDto candidateDto = new CandidateDto();
        CandidateDto savedCandidateDto = new CandidateDto();
        when(candidateService.saveCandidate(candidateDto)).thenReturn(savedCandidateDto);

        CandidateDto result = candidateController.createCandidate(candidateDto);

        assertNotNull(result);
        assertEquals(savedCandidateDto, result);
        verify(candidateService, times(1)).saveCandidate(candidateDto);
    }

    @Test
    public void testGetAllCandidates() {
        CandidateDto candidateDto1 = new CandidateDto();
        CandidateDto candidateDto2 = new CandidateDto();
        when(candidateService.getAllCandidates()).thenReturn(Arrays.asList(candidateDto1, candidateDto2));

        List<CandidateDto> result = candidateController.getAllCandidates();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(candidateDto1, result.get(0));
        assertEquals(candidateDto2, result.get(1));
        verify(candidateService, times(1)).getAllCandidates();
    }

    @Test
    public void testGetCandidateById() {
        int candidateId = 1;
        CandidateDto candidateDto = new CandidateDto();
        when(candidateService.getCandidateById(candidateId)).thenReturn(candidateDto);

        CandidateDto result = candidateController.getCandidateById(candidateId);

        assertNotNull(result);
        assertEquals(candidateDto, result);
        verify(candidateService, times(1)).getCandidateById(candidateId);
    }

    @Test
    public void testGetCandidateByUsername() {
        String username = "DianaH";
        CandidateDto candidateDto = new CandidateDto();
        when(candidateService.getCandidateByUsername(username)).thenReturn(candidateDto);

        CandidateDto result = candidateController.getCandidateByUsername(username);

        assertNotNull(result);
        assertEquals(candidateDto, result);
        verify(candidateService, times(1)).getCandidateByUsername(username);
    }

    @Test
    public void testSearchCandidates() {
        Map<String, Object> parameters = new HashMap<>();
        CandidateDto candidateDto1 = new CandidateDto();
        CandidateDto candidateDto2 = new CandidateDto();
        List<CandidateDto> expectedCandidates = Arrays.asList(candidateDto1, candidateDto2);
        when(candidateService.filterCandidatesByAnyField(parameters)).thenReturn(expectedCandidates);

        List<CandidateDto> result = candidateController.searchCandidates(parameters);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(candidateDto1, result.get(0));
        assertEquals(candidateDto2, result.get(1));
        verify(candidateService, times(1)).filterCandidatesByAnyField(parameters);
    }

    @Test
    public void testDeleteCandidate() {
        int candidateId = 1;

        candidateController.deleteCandidate(candidateId);

        verify(candidateService, times(1)).deleteCandidateById(candidateId);
    }

    @Test
    public void testUpdateCandidate() throws ParseException {
        int candidateId = 1;
        CandidateDto candidateDto = new CandidateDto();

        candidateController.updateCandidate(candidateId, candidateDto);

        verify(candidateService, times(1)).updateCandidate(candidateId, candidateDto);
    }

    @Test
    public void testPatchUpdateCandidate() throws ParseException {
        int candidateId = 1;
        CandidateDto candidateDto = new CandidateDto();

        candidateController.patchUpdateCandidate(candidateId, candidateDto);

        verify(candidateService, times(1)).patchCandidate(candidateId, candidateDto);
    }
}
