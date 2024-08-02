package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.JobDto;

import java.util.List;

public interface JobService {

    JobDto saveJob(JobDto jobDto);

    List<JobDto> getAllJobs();

    JobDto getJobById(int jobId);
}
