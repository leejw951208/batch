package com.example.batch.job.parameter

import com.example.batch.member.enmeration.MemberStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.util.StringUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class RequestJobParameter {

    private var requestDateTime: LocalDateTime? = null
    private var birthDay: LocalDate? = null
    private var status: MemberStatus? = null

    @Value("#{jobParameters[requestDateTime]}")
    fun setRequestDateTime(strRequestDateTime: String?) =
            strRequestDateTime?.let { requestDateTime = LocalDateTime.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) }

    @Value("#{jobParameters[birthDay]}")
    fun setBirthDay(strBirthDay: String?) =
        strBirthDay?.let { birthDay = LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd")) }

    @Value("#{jobParameters[status]}")
    fun setStatus(strStatus: String?) = strStatus?.let { status = MemberStatus.valueOf(it) }

    open fun getRequestDateTime() = requestDateTime
    open fun getBirthDay() = birthDay
    open fun getStatus() = status
}