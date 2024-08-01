package com.nagarro.si.cm.util;

import com.nagarro.si.cm.dto.JobDto;
import com.nagarro.si.cm.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public Job toJob(JobDto jobDto) {
        Job job = new Job();
        job.setTitle(jobDto.getTitle());
        return job;
    }

    public JobDto toDTO(Job job) {
        JobDto jobDto = new JobDto();
        jobDto.setId(job.getId());
        jobDto.setTitle(job.getTitle());
        return jobDto;
    }
}
