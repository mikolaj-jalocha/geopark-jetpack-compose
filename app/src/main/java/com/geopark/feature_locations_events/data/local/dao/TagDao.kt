package com.geopark.feature_locations_events.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.geopark.feature_locations_events.data.local.entity.OrganizerEntity
import com.geopark.feature_locations_events.data.local.entity.TagEntity

@Dao
interface TagDao : BaseDao<TagEntity> {

    @Query("SELECT * FROM TagEntity")
    suspend fun getTags(): List<TagEntity>

    @Query("SELECT * FROM TagEntity WHERE tagId=:tagId")
    suspend fun getTagById(tagId: String): TagEntity?

}