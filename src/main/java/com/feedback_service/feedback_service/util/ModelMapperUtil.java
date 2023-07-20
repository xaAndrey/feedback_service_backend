package com.feedback_service.feedback_service.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperUtil {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}