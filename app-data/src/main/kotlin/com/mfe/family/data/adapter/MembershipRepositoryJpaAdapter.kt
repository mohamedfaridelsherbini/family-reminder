package com.mfe.family.data.adapter

import com.mfe.family.data.jpa.repo.MembershipJpaRepo
import com.mfe.family.data.mapper.toDomain
import com.mfe.family.data.mapper.toJpa
import com.mfe.family.domain.entity.FamilyId
import com.mfe.family.domain.entity.Membership
import com.mfe.family.domain.port.MembershipRepository
import org.springframework.stereotype.Component

@Component
class MembershipRepositoryJpaAdapter(
    private val membershipJpaRepo: MembershipJpaRepo
) : MembershipRepository {
    override fun add(membership: Membership) {
        membershipJpaRepo.save(membership.toJpa())
    }

    override fun list(familyId: FamilyId): List<Membership> =
        membershipJpaRepo.findAllByFamilyId(familyId.value).map { it.toDomain() }
}
