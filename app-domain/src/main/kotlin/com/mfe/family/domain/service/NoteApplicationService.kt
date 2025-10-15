package com.mfe.family.domain.service

import com.mfe.family.domain.entity.FamilyId
import com.mfe.family.domain.entity.Note
import com.mfe.family.domain.port.NoteQueryService
import com.mfe.family.domain.port.NoteRepository

class NoteApplicationService(
    private val noteRepository: NoteRepository
) : NoteQueryService {
    override fun list(familyId: FamilyId): List<Note> =
        noteRepository.list(familyId)
}
