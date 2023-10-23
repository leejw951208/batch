package com.example.batch.job

import com.example.batch.member.MemberStatus
import org.springframework.beans.factory.annotation.Value
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class RequestJobParameter(
    private val requestDate: LocalDateTime,
    private val status: MemberStatus
) {
    open fun getRequestDate() = requestDate
    open fun getStatus() = status
}