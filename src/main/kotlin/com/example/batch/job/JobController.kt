package com.example.batch.job

import com.example.batch.member.MemberStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.batch.core.repository.JobRestartException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
class JobController(
    private val jobLauncher: JobLauncher,
    private val job: Job
) {
    @GetMapping("/job")
    @Throws(JobInstanceAlreadyCompleteException::class, JobExecutionAlreadyRunningException::class,
        JobParametersInvalidException::class, JobRestartException::class)
    fun testJob(): String {
        val jobParametersBuilder = JobParametersBuilder()
            .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .addString("status", MemberStatus.SLEEP.name)
            .toJobParameters()

        val jobExecution = jobLauncher.run(job, jobParametersBuilder)
        return jobExecution.toString()
    }
}