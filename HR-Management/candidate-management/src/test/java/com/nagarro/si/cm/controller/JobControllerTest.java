package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.dto.JobDto;
import com.nagarro.si.cm.service.JobService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobControllerTest {

    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    @Test
    public void testCreateJob() {
        JobDto jobDto = new JobDto();
        JobDto savedJobDto = new JobDto();
        when(jobService.saveJob(jobDto)).thenReturn(savedJobDto);

        JobDto result = jobController.createJob(jobDto);

        assertNotNull(result);
        assertEquals(savedJobDto, result);
        verify(jobService, times(1)).saveJob(jobDto);
    }

    @Test
    public void testGetAllJobs() {
        JobDto jobDto1 = new JobDto();
        JobDto jobDto2 = new JobDto();
        when(jobService.getAllJobs()).thenReturn(Arrays.asList(jobDto1, jobDto2));

        List<JobDto> result = jobController.getAllJobs();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(jobDto1, result.get(0));
        assertEquals(jobDto2, result.get(1));
        verify(jobService, times(1)).getAllJobs();
    }

    @Test
    public void testGetJobById() {
        int jobId = 1;
        JobDto jobDto = new JobDto();
        when(jobService.getJobById(jobId)).thenReturn(jobDto);

        JobDto result = jobController.getJobById(jobId);

        assertNotNull(result);
        assertEquals(jobDto, result);
        verify(jobService, times(1)).getJobById(jobId);
    }
}
