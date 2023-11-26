package com.example.batch

import com.example.batch.member.enmeration.MemberStatus
import com.example.batch.member.entity.Member
import com.example.batch.member.repository.MemberRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@SpringBootTest
class BatchApplicationTests @Autowired constructor(
		private val memberRepository: MemberRepository
) {

//	@Test
//	@Transactional
////	@Rollback(false)
//	fun contextLoads() {
////		for (i in 0..499) {
//			val members: MutableList<Member> = LinkedList()
//			for (j in 1..10000) {
//				members.add(Member("사람", MemberStatus.SLEEP, LocalDate.of(1995, 12, 8)))
//			}
//			memberRepository.saveAllAndFlush(members)
////		}
//	}
}
