package com.mfe.family.data.adapter

import com.mfe.family.data.jpa.repo.ReminderJpaRepo
import com.mfe.family.data.mapper.toDomain
import com.mfe.family.data.mapper.toJpa
import com.mfe.family.domain.entity.NoteId
import com.mfe.family.domain.entity.Reminder
import com.mfe.family.domain.entity.ReminderId
import com.mfe.family.domain.port.ReminderRepository
import org.springframework.stereotype.Component

@Component
class ReminderRepositoryJpaAdapter(
    private val reminderJpaRepo: ReminderJpaRepo
) : ReminderRepository {

    override fun create(reminder: Reminder): Reminder =
        reminderJpaRepo.save(reminder.toJpa()).toDomain()

    override fun delete(id: ReminderId) {
        reminderJpaRepo.deleteById(id.value)
    }

    override fun listByNote(noteId: NoteId): List<Reminder> =
        reminderJpaRepo.findAllByNoteId(noteId.value).map { it.toDomain() }
}
