package com.mfe.family.data.adapter

import com.mfe.family.data.jpa.repo.NoteJpaRepo
import com.mfe.family.data.mapper.toDomain
import com.mfe.family.data.mapper.toJpa
import com.mfe.family.domain.entity.FamilyId
import com.mfe.family.domain.entity.Note
import com.mfe.family.domain.entity.NoteId
import com.mfe.family.domain.port.NoteRepository
import org.springframework.stereotype.Component

@Component
class NoteRepositoryJpaAdapter(
    private val noteJpaRepo: NoteJpaRepo
) : NoteRepository {

    override fun create(note: Note): Note =
        noteJpaRepo.save(note.toJpa()).toDomain()

    override fun update(note: Note): Note =
        noteJpaRepo.save(note.toJpa()).toDomain()

    override fun delete(id: NoteId) {
        noteJpaRepo.deleteById(id.value)
    }

    override fun list(familyId: FamilyId): List<Note> =
        noteJpaRepo.findAllByFamilyId(familyId.value).map { it.toDomain() }
}
