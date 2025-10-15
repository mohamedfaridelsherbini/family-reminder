package com.mfe.family.api.controller

import com.mfe.family.api.dto.UserPublicDto
import com.mfe.family.api.mapper.toPublicDto
import com.mfe.family.domain.entity.User
import com.mfe.family.domain.entity.UserId
import com.mfe.family.domain.port.PasswordHasher
import com.mfe.family.domain.port.UserRepository
import com.mfe.family.infra.security.InvalidCredentialsException
import com.mfe.family.infra.security.JwtService
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val userRepository: UserRepository,
    private val passwordHasher: PasswordHasher,
    private val jwtService: JwtService
) {

    data class SignupRequest(
        @field:NotBlank
        val name: String,
        @field:Email
        val email: String,
        @field:Size(min = 8, message = "Password must be at least 8 characters")
        val password: String
    )

    data class LoginRequest(
        @field:Email
        val email: String,
        @field:NotBlank
        val password: String
    )

    data class AuthResponse(
        val token: String,
        val user: UserPublicDto
    )

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody request: SignupRequest): ResponseEntity<AuthResponse> {
        val user = userRepository.save(
            User(
                id = UserId(UUID.randomUUID().toString()),
                name = request.name,
                email = request.email,
                passwordHash = passwordHasher.hash(request.password)
            )
        )

        val token = jwtService.generate(user.id.value, emptyList())
        return ResponseEntity.ok(
            AuthResponse(
                token = token,
                user = user.toPublicDto()
            )
        )
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        val user = userRepository.findByEmail(request.email)
            ?: throw InvalidCredentialsException()

        if (!passwordHasher.matches(request.password, user.passwordHash)) {
            throw InvalidCredentialsException()
        }

        val token = jwtService.generate(user.id.value, emptyList())
        return ResponseEntity.ok(
            AuthResponse(
                token = token,
                user = user.toPublicDto()
            )
        )
    }
}
