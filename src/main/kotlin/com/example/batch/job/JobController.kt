package com.example.batch.job

import com.example.batch.member.Status
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
    fun testJob() {
        val createDate = LocalDate.now()
        val status = Status.SLEEP

        val jobParameters = JobParametersBuilder()
            .addString("createDate", createDate.toString())
            .addString("status", status.name)
            .toJobParameters()

        jobLauncher.run(job, jobParameters)

    }
}