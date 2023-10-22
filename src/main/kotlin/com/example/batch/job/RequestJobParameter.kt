package com.example.batch.job

import com.example.batch.member.MemberStatus
import org.springframework.beans.factory.annotation.Value
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class RequestJobParameter(
    private val requestDate: LocalDate,
    private val status: MemberStatus
) {
    fun getRequestDate() = requestDate
    fun getStatus() = status
}