package com.example.batch.member.entity

import com.example.batch.member.Status
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@DynamicUpdate
@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "t_member")
class Member (
    name: String?,
    status: Status?
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

    fun changeStatus(status: Status) {
        this.status = status
    }
}
