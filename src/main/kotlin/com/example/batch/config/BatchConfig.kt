package com.example.batch.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.batch.JobLauncherApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BatchConfig(
        @Value("\${spring.batch.job.name}")
        val jobName: String
) {
    @Bean
    fun jobLauncherApplicationRunner(
            jobLauncher: JobLauncher?, jobExplorer: JobExplorer?, jobRepository: JobRepository?, jobs: Collection<Job>?
    ): JobLauncherApplicationRunner {
        val runner = JobLauncherApplicationRunner(jobLauncher, jobExplorer, jobRepository)
        if (jobs == null || jobs.stream().noneMatch { job: Job -> job.name == jobName }) {
            throw IllegalArgumentException(jobName + "을 찾을 수 없습니다.")
        }
        runner.setJobName(jobName)
        return runner
    }
}