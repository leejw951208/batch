package com.example.batch.job

import com.example.batch.member.Status
import com.example.batch.member.entity.Member
import jakarta.persistence.EntityManagerFactory
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

const val JOB_NAME = "member"

@Configuration
class JobConfig(
    private val jobParameter: JobParameter,
    private val entityManagerFactory: EntityManagerFactory
) {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    @Bean(name = [JOB_NAME + "_jobParameter"])
    @JobScope
    fun jobParameter(): JobParameter {
        return JobParameter()
    }

    @Bean(name = [JOB_NAME + "_job"])
    fun job(jobRepository: JobRepository, platformTransactionManager: PlatformTransactionManager): Job {
        return JobBuilder(JOB_NAME + "_job", jobRepository)
            .start(step(jobRepository, platformTransactionManager))
            .build()
    }

    @Bean(name = [JOB_NAME + "_step"])
    @JobScope
    fun step(jobRepository: JobRepository, platformTransactionManager: PlatformTransactionManager) : Step {
        return StepBuilder(JOB_NAME + "_step", jobRepository)
            .chunk<Member, Member>(10, platformTransactionManager)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build()
    }

    @Bean(name = [JOB_NAME + "_reader"])
    @StepScope
    fun reader(): JpaPagingItemReader<Member> {
        val params = HashMap<String, Any>()
        params["createDate"] = jobParameter.getCreateDate()
        params["status"] = jobParameter.getStatus()

        return JpaPagingItemReaderBuilder<Member>()
            .name(JOB_NAME + "_reader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(10)
            .queryString("select m from Member m where m.status = :status")
            .parameterValues(params)
            .build()
    }

    @Bean(name = [JOB_NAME + "_processor"])
    @StepScope
    fun processor(): ItemProcessor<Member, Member> {
        return ItemProcessor<Member, Member> { member ->
            member.changeStatus(Status.WITHDRAW)
            member
        }
    }

    @Bean(name = [JOB_NAME + "_writer"])
    @StepScope
    fun writer(): JpaItemWriter<Member> {
        return JpaItemWriterBuilder<Member>()
            .entityManagerFactory(entityManagerFactory)
            .build()
    }
}