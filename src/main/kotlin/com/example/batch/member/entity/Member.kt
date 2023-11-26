package com.example.batch.member.entity

import com.example.batch.member.enmeration.MemberStatus
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_member")
class Member (
        name: String?,
        status: MemberStatus?,
        birthDay: LocalDate
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
        protected set

    @Column(name = "name")
    var name = name
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status = status
        protected set

    @Column(name = "birth_day")
    var birthDay = birthDay
        protected set

    fun changeStatus(status: MemberStatus) {
        this.status = status
    }
}
