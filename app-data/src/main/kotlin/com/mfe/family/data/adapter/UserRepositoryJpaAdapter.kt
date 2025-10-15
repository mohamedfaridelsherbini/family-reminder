package com.mfe.family.data.adapter

import com.mfe.family.data.jpa.repo.UserJpaRepo
import com.mfe.family.data.mapper.toDomain
import com.mfe.family.data.mapper.toJpa
import com.mfe.family.domain.entity.User
import com.mfe.family.domain.port.UserRepository
import org.springframework.stereotype.Component

@Component
class UserRepositoryJpaAdapter(
    private val userJpaRepo: UserJpaRepo
) : UserRepository {
    override fun findByEmail(email: String): User? =
        userJpaRepo.findByEmail(email)?.toDomain()

    override fun save(user: User): User =
        userJpaRepo.save(user.toJpa()).toDomain()
}
