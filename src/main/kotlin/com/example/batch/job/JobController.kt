package com.example.batch.job

import com.example.batch.member.MemberStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class JobController(
    private val jobLauncher: JobLauncher,
    private val job: Job
) {
    @GetMapping("/job")
    fun testJob(): String {
        val jobParametersBuilder = JobParametersBuilder()
            .addString("requestDate", LocalDate.now().toString())
            .addString("status", MemberStatus.SLEEP.name)
            .toJobParameters()

        val jobExecution = jobLauncher.run(job, jobParametersBuilder)
        return jobExecution.toString()
    }
}