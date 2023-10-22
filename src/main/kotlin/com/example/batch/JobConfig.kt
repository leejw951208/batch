package com.example.batch

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.util.*


@Configuration
class JobConfig {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean
    fun commandLineRunner(jobLauncher: JobLauncher, job: Job?): CommandLineRunner? {
        return CommandLineRunner {
            println("Job Started !!")
            val paramsBuilder = JobParametersBuilder()
            paramsBuilder.addString("currentTime", java.lang.String.valueOf(Date()))
            val parameters = paramsBuilder.toJobParameters()
            val jobExecution = jobLauncher.run(job!!, parameters)
            println("Job Status : " + jobExecution.status)
        }
    }

    @Bean
    fun firstJob(jobRepository: JobRepository, platformTransactionManager: PlatformTransactionManager): Job {
        return JobBuilder("firstJob", jobRepository)
            .start(firstStep(jobRepository, platformTransactionManager))
            .build()
    }

    @Bean
    fun firstStep(jobRepository: JobRepository, platformTransactionManager: PlatformTransactionManager) : Step {
        return StepBuilder("firstStep", jobRepository)
            .chunk<String, String>(10, platformTransactionManager)
            .reader(itemReader())
            .writer(itemWriter())
            .build()
    }

    @Bean
    fun itemReader(): ItemReader<String> {
        return ItemReader<String> { "read ok" }
    }

    @Bean
    fun itemWriter() = ItemWriter<String> { items -> log.info("Write items: {}", items) }
}