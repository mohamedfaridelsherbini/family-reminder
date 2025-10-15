package com.mfe.family.domain.usecase

import com.mfe.family.domain.entity.FamilyId
import com.mfe.family.domain.entity.Note
import com.mfe.family.domain.entity.NoteId
import com.mfe.family.domain.port.NoteRepository
import java.time.Instant
import java.util.UUID

class CreateNoteUseCase(
    private val noteRepository: NoteRepository
) {
    fun execute(command: Command): Note {
        val note = Note(
            id = NoteId(UUID.randomUUID().toString()),
            familyId = command.familyId,
            title = command.title,
            body = command.body,
            tags = command.tags,
            dueAt = command.dueAt
        )
        return noteRepository.create(note)
    }

    data class Command(
        val familyId: FamilyId,
        val title: String,
        val body: String?,
        val tags: List<String>,
        val dueAt: Instant?
    )
}
