package com.mfe.family.infra.security

import com.mfe.family.domain.port.PasswordHasher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class BCryptPasswordHasher(
    private val encoder: PasswordEncoder
) : PasswordHasher {
    override fun hash(value: String): String = encoder.encode(value)
    override fun matches(raw: String, hash: String): Boolean = encoder.matches(raw, hash)
}
