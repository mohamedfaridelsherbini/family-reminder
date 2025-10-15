package com.mfe.family.infra.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.time.Duration
import java.time.Instant
import java.util.Date
import javax.crypto.SecretKey

class JwtService(
    secret: String
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generate(
        userId: String,
        familyIds: List<String>,
        ttl: Duration = Duration.ofHours(2)
    ): String {
        val issuedAt = Instant.now()
        val expiration = issuedAt.plus(ttl)
        return Jwts.builder()
            .claim("uid", userId)
            .claim("fids", familyIds)
            .issuedAt(Date.from(issuedAt))
            .expiration(Date.from(expiration))
            .signWith(key)
            .compact()
    }

    fun parse(token: String): Map<String, Any?> {
        val claims = try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (ex: JwtException) {
            throw InvalidJwtException(ex)
        }

        val families = when (val raw = claims["fids"]) {
            is Collection<*> -> raw.filterIsInstance<String>()
            is String -> raw.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            else -> emptyList()
        }

        return mapOf(
            "uid" to claims["uid"],
            "fids" to families,
            "exp" to claims.expiration?.toInstant()
        )
    }

    class InvalidJwtException(cause: Throwable) : RuntimeException(cause)
}
