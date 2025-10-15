package com.mfe.family.data.adapter

import com.mfe.family.data.jpa.repo.LocationJpaRepo
import com.mfe.family.data.mapper.toDomain
import com.mfe.family.data.mapper.toJpa
import com.mfe.family.domain.entity.LocationTag
import com.mfe.family.domain.entity.UserId
import com.mfe.family.domain.port.LocationRepository
import org.springframework.stereotype.Component

@Component
class LocationRepositoryJpaAdapter(
    private val locationJpaRepo: LocationJpaRepo
) : LocationRepository {
    override fun create(location: LocationTag): LocationTag =
        locationJpaRepo.save(location.toJpa()).toDomain()

    override fun listByUser(userId: UserId): List<LocationTag> =
        locationJpaRepo.findAllByUserId(userId.value).map { it.toDomain() }
}
