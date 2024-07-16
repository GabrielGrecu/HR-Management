package com.nagarro.si.cm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CandidateMapperConfig {

    @Bean
    public CandidateMapper modelMapper() {
        return new CandidateMapper();
    }
}
