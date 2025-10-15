package com.mfe.family.infra.security

data class JwtPrincipal(
    val userId: String,
    val familyIds: List<String>
)
