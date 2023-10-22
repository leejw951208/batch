package com.example.batch.member.service

import com.example.batch.member.MemberStatus
import com.example.batch.member.entity.Member
import com.example.batch.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun saveAll() {
        val members: MutableList<Member> = mutableListOf()
        for(i: Int in 1..50)
            members.add(Member("name$i", MemberStatus.SLEEP))

        memberRepository.saveAll(members)
    }
}