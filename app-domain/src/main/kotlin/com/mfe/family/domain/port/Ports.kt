package com.mfe.family.domain.port

import com.mfe.family.domain.entity.*

interface UserRepository {
    fun findByEmail(email: String): User?
    fun save(user: User): User
}

interface FamilyRepository {
    fun create(family: Family): Family
    fun findById(id: FamilyId): Family?
}

interface MembershipRepository {
    fun add(membership: Membership)
    fun list(familyId: FamilyId): List<Membership>
}

interface NoteRepository {
    fun create(note: Note): Note
    fun update(note: Note): Note
    fun delete(id: NoteId)
    fun list(familyId: FamilyId): List<Note>
}

interface ReminderRepository {
    fun create(reminder: Reminder): Reminder
    fun delete(id: ReminderId)
    fun listByNote(noteId: NoteId): List<Reminder>
}

interface LocationRepository {
    fun create(location: LocationTag): LocationTag
    fun listByUser(userId: UserId): List<LocationTag>
}

interface PasswordHasher {
    fun hash(value: String): String
    fun matches(raw: String, hash: String): Boolean
}

interface NoteQueryService {
    fun list(familyId: FamilyId): List<Note>
}
