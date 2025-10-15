package com.mfe.family.data.jpa.repo

import com.mfe.family.data.jpa.entity.FamilyJpa
import com.mfe.family.data.jpa.entity.LocationJpa
import com.mfe.family.data.jpa.entity.MembershipJpa
import com.mfe.family.data.jpa.entity.MembershipPk
import com.mfe.family.data.jpa.entity.NoteJpa
import com.mfe.family.data.jpa.entity.ReminderJpa
import com.mfe.family.data.jpa.entity.UserJpa
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepo : JpaRepository<UserJpa, String> {
    fun findByEmail(email: String): UserJpa?
}

interface FamilyJpaRepo : JpaRepository<FamilyJpa, String>

interface MembershipJpaRepo : JpaRepository<MembershipJpa, MembershipPk> {
    fun findAllByFamilyId(familyId: String): List<MembershipJpa>
}

interface NoteJpaRepo : JpaRepository<NoteJpa, String> {
    fun findAllByFamilyId(familyId: String): List<NoteJpa>
}

interface ReminderJpaRepo : JpaRepository<ReminderJpa, String> {
    fun findAllByNoteId(noteId: String): List<ReminderJpa>
}

interface LocationJpaRepo : JpaRepository<LocationJpa, String> {
    fun findAllByUserId(userId: String): List<LocationJpa>
}
