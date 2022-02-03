package com.geopark.feature_locations_events.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.geopark.feature_locations_events.data.local.entity.CategoryEntity
import com.geopark.feature_locations_events.data.local.entity.LabelEntity

@Dao
interface CategoryDao : BaseDao<CategoryEntity> {

    @Query("SELECT * FROM CategoryEntity")
    suspend fun getCategories() : List<CategoryEntity>

    @Query("DELETE FROM CategoryEntity")
    suspend fun delete()



}