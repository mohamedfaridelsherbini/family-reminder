package com.mfe.family.data.mapper

import com.mfe.family.data.jpa.entity.FamilyJpa
import com.mfe.family.data.jpa.entity.LocationJpa
import com.mfe.family.data.jpa.entity.MembershipJpa
import com.mfe.family.data.jpa.entity.NoteJpa
import com.mfe.family.data.jpa.entity.ReminderJpa
import com.mfe.family.data.jpa.entity.UserJpa
import com.mfe.family.domain.entity.Family
import com.mfe.family.domain.entity.FamilyId
import com.mfe.family.domain.entity.LocationId
import com.mfe.family.domain.entity.LocationTag
import com.mfe.family.domain.entity.Membership
import com.mfe.family.domain.entity.Note
import com.mfe.family.domain.entity.NoteId
import com.mfe.family.domain.entity.Reminder
import com.mfe.family.domain.entity.ReminderId
import com.mfe.family.domain.entity.User
import com.mfe.family.domain.entity.UserId

private const val TAG_SEPARATOR = ","

fun UserJpa.toDomain(): User = User(
    id = UserId(id),
    name = name,
    email = email,
    passwordHash = passwordHash
)

fun User.toJpa(): UserJpa = UserJpa(
    id = id.value,
    name = name,
    email = email,
    passwordHash = passwordHash
)

fun FamilyJpa.toDomain(): Family = Family(
    id = FamilyId(id),
    name = name,
    ownerId = UserId(ownerId)
)

fun Family.toJpa(): FamilyJpa = FamilyJpa(
    id = id.value,
    name = name,
    ownerId = ownerId.value
)

fun MembershipJpa.toDomain(): Membership = Membership(
    userId = UserId(userId),
    familyId = FamilyId(familyId),
    role = role
)

fun Membership.toJpa(): MembershipJpa = MembershipJpa(
    userId = userId.value,
    familyId = familyId.value,
    role = role
)

fun NoteJpa.toDomain(): Note = Note(
    id = NoteId(id),
    familyId = FamilyId(familyId),
    title = title,
    body = body,
    tags = if (tags.isBlank()) emptyList() else tags.split(TAG_SEPARATOR).map { it.trim() }.filter { it.isNotEmpty() },
    dueAt = dueAt
)

fun Note.toJpa(): NoteJpa = NoteJpa(
    id = id.value,
    familyId = familyId.value,
    title = title,
    body = body,
    tags = tags.joinToString(TAG_SEPARATOR),
    dueAt = dueAt
)

fun ReminderJpa.toDomain(): Reminder = Reminder(
    id = ReminderId(id),
    noteId = NoteId(noteId),
    type = type,
    triggerAt = triggerAt,
    locationId = locationId?.let(::LocationId)
)

fun Reminder.toJpa(): ReminderJpa = ReminderJpa(
    id = id.value,
    noteId = noteId.value,
    type = type,
    triggerAt = triggerAt,
    locationId = locationId?.value
)

fun LocationJpa.toDomain(): LocationTag = LocationTag(
    id = LocationId(id),
    label = label,
    lat = lat,
    lng = lng,
    radiusMeters = radiusMeters,
    ownerId = UserId(userId)
)

fun LocationTag.toJpa(userId: String = ownerId?.value ?: error("Location owner id required")): LocationJpa = LocationJpa(
    id = id.value,
    userId = userId,
    label = label,
    lat = lat,
    lng = lng,
    radiusMeters = radiusMeters
)
