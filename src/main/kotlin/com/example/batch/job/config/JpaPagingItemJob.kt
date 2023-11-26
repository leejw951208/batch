package com.example.batch.job.config

import com.example.batch.job.parameter.RequestJobParameter
import com.example.batch.member.enmeration.MemberStatus
import com.example.batch.member.entity.Member
import jakarta.persistence.EntityManagerFactory
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

@Configuration
class JpaPagingItemJob(
        private val entityManagerFactory: EntityManagerFactory,
        private val requestJobParameter: RequestJobParameter
) {
    private final val chunkSize = 10

    @Bean
    fun jpaPagingJob(jobRepository: JobRepository, transactionManager: PlatformTransactionManager): Job {
        return JobBuilder("jpaPagingJob", jobRepository)
                .start(jpaPagingStep(jobRepository, transactionManager))
                .build()
    }

    @Bean
    @JobScope
    fun jpaPagingStep(jobRepository: JobRepository, transactionManager: PlatformTransactionManager): Step {
        return StepBuilder("jpaPagingStep", jobRepository)
                .chunk<Member, Member>(chunkSize, transactionManager)
                .reader(jpaPagingItemReader())
                .processor(jpaPagingItemProcessor())
                .writer(jpaPagingItemWriter())
                .build()
    }

    @Bean
    @StepScope
    fun jpaPagingItemReader(): JpaPagingItemReader<Member> {
        val parameterValues: MutableMap<String, Any> = HashMap()
        requestJobParameter.getBirthDay()?.let { parameterValues["birthDay"] = it }

        return JpaPagingItemReaderBuilder<Member>()
                .name("jpaPagingItemReader")
                .queryString("select m from Member m where m.birthDay = :birthDay")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .parameterValues(parameterValues)
                .build()
    }

    @Bean
    @StepScope
    fun jpaPagingItemProcessor(): ItemProcessor<Member, Member> {
        return ItemProcessor<Member, Member> { member: Member ->
            member.changeStatus(MemberStatus.WITHDRAW)
            member
        }
    }

    @Bean
    @StepScope
    fun jpaPagingItemWriter(): JpaItemWriter<Member> {
        return JpaItemWriterBuilder<Member>()
                .entityManagerFactory(entityManagerFactory)
                .build()
    }
}