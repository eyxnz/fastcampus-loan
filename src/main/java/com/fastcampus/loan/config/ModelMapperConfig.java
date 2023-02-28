package com.fastcampus.loan.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // 매핑 전략 설정 : 정확히 일치하는 필드만 매핑, null 필드는 생략
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        return modelMapper;
    }
}
