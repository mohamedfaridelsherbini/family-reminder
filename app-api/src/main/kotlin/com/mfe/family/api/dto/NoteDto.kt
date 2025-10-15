package com.mfe.family.api.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.Instant

data class NoteDto(
    val id: String? = null,
    @field:NotBlank
    @field:Size(max = 120)
    val title: String,
    val body: String? = null,
    val tags: List<String> = emptyList(),
    val dueAt: Instant? = null,
    @field:NotBlank
    val familyId: String
)
