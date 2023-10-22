package com.example.batch.job

import com.example.batch.member.Status
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class JobParameter {

    private lateinit var createDate: LocalDate
    private lateinit var status: Status

    @Value("#{jobParameters[createDate]}")
    fun setCreateDate(createDate: String) {
        this.createDate = LocalDate.parse(createDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    @Value("#{jobParameters[status]}")
    fun setStatus(status: String) {
        this.status = Status.valueOf(status)
    }

    fun getCreateDate(): LocalDate {
        return createDate
    }

    fun getStatus(): Status {
        return status
    }
}