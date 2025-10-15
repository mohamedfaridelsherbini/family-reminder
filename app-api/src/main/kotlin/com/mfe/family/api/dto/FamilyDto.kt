package com.mfe.family.api.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class FamilyDto(
    val id: String? = null,
    @field:NotBlank
    @field:Size(max = 120)
    val name: String
)
