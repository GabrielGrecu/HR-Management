package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.JobDto;
import com.nagarro.si.cm.entity.Job;
import com.nagarro.si.cm.exception.EntityNotFoundException;
import com.nagarro.si.cm.repository.JobRepository;
import com.nagarro.si.cm.util.JobMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobMapper jobMapper;

    @InjectMocks
    private JobServiceImpl jobService;

    private Job job;
    private JobDto jobDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        job = new Job();
        job.setId(1);
        job.setTitle("Software Engineer");

        jobDto = new JobDto();
        jobDto.setId(1);
        jobDto.setTitle("Software Engineer");
    }

    @Test
    void testSaveJob_Success() {
        when(jobMapper.toJob(jobDto)).thenReturn(job);
        when(jobRepository.save(job)).thenReturn(job);
        when(jobMapper.toDTO(job)).thenReturn(jobDto);

        JobDto savedJob = jobService.saveJob(jobDto);

        assertNotNull(savedJob);
        assertEquals(jobDto, savedJob);
        verify(jobRepository).save(job);
    }

    @Test
    void testGetAllJobs() {
        when(jobRepository.findAll()).thenReturn(List.of(job));
        when(jobMapper.toDTO(job)).thenReturn(jobDto);

        List<JobDto> jobs = jobService.getAllJobs();

        assertNotNull(jobs);
        assertEquals(1, jobs.size());
        verify(jobRepository).findAll();
    }

    @Test
    void testGetJobById_Success() {
        when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));
        when(jobMapper.toDTO(job)).thenReturn(jobDto);

        JobDto foundJob = jobService.getJobById(job.getId());

        assertNotNull(foundJob);
        assertEquals(jobDto, foundJob);
        verify(jobRepository).findById(job.getId());
    }

    @Test
    void testGetJobById_NotFound() {
        when(jobRepository.findById(job.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> jobService.getJobById(job.getId()));
    }
}
