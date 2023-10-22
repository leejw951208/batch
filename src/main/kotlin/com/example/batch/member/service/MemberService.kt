package com.example.batch.member.service

import com.example.batch.member.Status
import com.example.batch.member.entity.Member
import com.example.batch.member.repository.MemberRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun saveAll() {
        val members: MutableList<Member> = mutableListOf()
        for(i: Int in 1..50)
            members.add(Member("name$i", Status.SLEEP))

        memberRepository.saveAll(members)
    }
}