package com.mfe.family.domain.entity

import java.time.Instant

@JvmInline
value class UserId(val value: String)

data class User(
    val id: UserId,
    val name: String,
    val email: String,
    val passwordHash: String
)

@JvmInline
value class FamilyId(val value: String)

data class Family(
    val id: FamilyId,
    val name: String,
    val ownerId: UserId
)

data class Membership(
    val userId: UserId,
    val familyId: FamilyId,
    val role: Role
)

enum class Role {
    OWNER,
    MEMBER,
    VIEWER
}

@JvmInline
value class NoteId(val value: String)

data class Note(
    val id: NoteId,
    val familyId: FamilyId,
    val title: String,
    val body: String?,
    val tags: List<String>,
    val dueAt: Instant?
)

@JvmInline
value class ReminderId(val value: String)

enum class ReminderType {
    TIME,
    LOCATION
}

data class Reminder(
    val id: ReminderId,
    val noteId: NoteId,
    val type: ReminderType,
    val triggerAt: Instant?,
    val locationId: LocationId?
)

@JvmInline
value class LocationId(val value: String)

data class LocationTag(
    val id: LocationId,
    val label: String,
    val lat: Double,
    val lng: Double,
    val radiusMeters: Int,
    val ownerId: UserId? = null
)
