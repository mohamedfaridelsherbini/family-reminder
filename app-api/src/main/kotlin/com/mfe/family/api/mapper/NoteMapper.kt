package com.mfe.family.api.mapper

import com.mfe.family.api.dto.FamilyDto
import com.mfe.family.api.dto.NoteDto
import com.mfe.family.api.dto.UserPublicDto
import com.mfe.family.domain.entity.Family
import com.mfe.family.domain.entity.FamilyId
import com.mfe.family.domain.entity.Note
import com.mfe.family.domain.entity.NoteId
import com.mfe.family.domain.entity.User
import com.mfe.family.domain.usecase.CreateNoteUseCase
import java.util.UUID

fun Note.toDto(): NoteDto = NoteDto(
    id = id.value,
    title = title,
    body = body,
    tags = tags,
    dueAt = dueAt,
    familyId = familyId.value
)

fun NoteDto.toDomain(): Note = Note(
    id = id?.let(::NoteId) ?: NoteId(UUID.randomUUID().toString()),
    familyId = FamilyId(familyId),
    title = title,
    body = body,
    tags = tags,
    dueAt = dueAt
)

fun NoteDto.toCommand(): CreateNoteUseCase.Command = CreateNoteUseCase.Command(
    familyId = FamilyId(familyId),
    title = title,
    body = body,
    tags = tags,
    dueAt = dueAt
)

fun Family.toDto(): FamilyDto = FamilyDto(
    id = id.value,
    name = name
)

fun User.toPublicDto(): UserPublicDto = UserPublicDto(
    id = id.value,
    name = name
)
