package com.geopark.feature_locations_events.data.local.dao

import androidx.room.*
import com.geopark.feature_locations_events.data.local.entity.LabelEntity
import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.entity.OrganizerEntity

@Dao
interface LabelDao : BaseDao<LabelEntity> {

    @Query("SELECT * FROM LabelEntity")
    suspend fun getLabels() : List<LabelEntity>

    @Query("SELECT * FROM LabelEntity WHERE labelId=:labelId")
    suspend fun getLabelById(labelId: String): LabelEntity

}