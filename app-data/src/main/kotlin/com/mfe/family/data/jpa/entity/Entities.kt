package com.mfe.family.data.jpa.entity

import com.mfe.family.domain.entity.ReminderType
import com.mfe.family.domain.entity.Role
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.io.Serializable
import java.time.Instant

@Entity
@Table(name = "users")
class UserJpa(
    @Id
    val id: String,
    val name: String,
    @Column(unique = true)
    val email: String,
    val passwordHash: String
)

@Entity
@Table(name = "families")
class FamilyJpa(
    @Id
    val id: String,
    val name: String,
    val ownerId: String
)

@IdClass(MembershipPk::class)
@Entity
@Table(name = "memberships")
class MembershipJpa(
    @Id
    val userId: String,
    @Id
    val familyId: String,
    @Enumerated(EnumType.STRING)
    val role: Role
)

class MembershipPk(
    var userId: String? = null,
    var familyId: String? = null
) : Serializable

@Entity
@Table(name = "notes")
class NoteJpa(
    @Id
    val id: String,
    val familyId: String,
    val title: String,
    @Column(columnDefinition = "text")
    val body: String?,
    @Column(columnDefinition = "text")
    val tags: String,
    val dueAt: Instant?
)

@Entity
@Table(name = "reminders")
class ReminderJpa(
    @Id
    val id: String,
    val noteId: String,
    @Enumerated(EnumType.STRING)
    val type: ReminderType,
    val triggerAt: Instant?,
    val locationId: String?
)

@Entity
@Table(name = "locations")
class LocationJpa(
    @Id
    val id: String,
    val userId: String,
    val label: String,
    val lat: Double,
    val lng: Double,
    val radiusMeters: Int
)
