package com.mfe.family.domain.usecase

import com.mfe.family.domain.entity.User
import com.mfe.family.domain.entity.UserId
import com.mfe.family.domain.port.PasswordHasher
import com.mfe.family.domain.port.UserRepository
import java.util.UUID

class SignupUseCase(
    private val userRepository: UserRepository,
    private val passwordHasher: PasswordHasher
) {
    fun execute(command: Command): User {
        val user = User(
            id = UserId(UUID.randomUUID().toString()),
            name = command.name,
            email = command.email,
            passwordHash = passwordHasher.hash(command.password)
        )
        return userRepository.save(user)
    }

    data class Command(
        val name: String,
        val email: String,
        val password: String
    )
}
