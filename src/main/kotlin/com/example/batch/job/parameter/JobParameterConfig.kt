package com.example.batch.job.parameter

import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class JobParameterConfig {

    @Bean
    @JobScope
    fun requestJobParamter(): RequestJobParameter {
        return RequestJobParameter()
    }
}