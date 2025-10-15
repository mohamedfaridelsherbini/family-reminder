package com.mfe.family.data.adapter

import com.mfe.family.data.jpa.repo.FamilyJpaRepo
import com.mfe.family.data.mapper.toDomain
import com.mfe.family.data.mapper.toJpa
import com.mfe.family.domain.entity.Family
import com.mfe.family.domain.entity.FamilyId
import com.mfe.family.domain.port.FamilyRepository
import org.springframework.stereotype.Component

@Component
class FamilyRepositoryJpaAdapter(
    private val familyJpaRepo: FamilyJpaRepo
) : FamilyRepository {
    override fun create(family: Family): Family =
        familyJpaRepo.save(family.toJpa()).toDomain()

    override fun findById(id: FamilyId): Family? =
        familyJpaRepo.findById(id.value).orElse(null)?.toDomain()
}
