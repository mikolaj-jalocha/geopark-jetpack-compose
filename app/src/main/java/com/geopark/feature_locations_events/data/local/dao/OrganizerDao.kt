package com.geopark.feature_locations_events.data.local.dao

import androidx.room.*
import com.geopark.feature_locations_events.data.local.entity.OrganizerEntity

@Dao
interface OrganizerDao : BaseDao<OrganizerEntity> {

    @Query("SELECT * FROM OrganizerEntity")
    suspend fun getOrganizers(): List<OrganizerEntity>

    @Query("SELECT * FROM OrganizerEntity WHERE organizerId=:organizerId")
    suspend fun getOrganizerById(organizerId: String): OrganizerEntity?

    @Query("DELETE FROM OrganizerEntity")
    suspend fun delete()


}