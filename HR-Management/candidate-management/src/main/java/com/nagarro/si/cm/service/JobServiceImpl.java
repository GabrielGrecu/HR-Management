package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.JobDto;
import com.nagarro.si.cm.entity.Job;
import com.nagarro.si.cm.exception.EntityNotFoundException;
import com.nagarro.si.cm.repository.JobRepository;
import com.nagarro.si.cm.util.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }

    @Override
    public JobDto saveJob(JobDto jobDto) {
        Job job = jobMapper.toJob(jobDto);
        Job savedJob = jobRepository.save(job);
        return jobMapper.toDTO(savedJob);
    }

    @Override
    public List<JobDto> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(jobMapper::toDTO)
                .toList();
    }

    @Override
    public JobDto getJobById(int jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Job with ID " + jobId + " not found"));
        return jobMapper.toDTO(job);
    }
}
