package com.example.batch.member.controller

import com.example.batch.member.service.MemberService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/members")
    fun init() {
        memberService.saveAll();
    }
}