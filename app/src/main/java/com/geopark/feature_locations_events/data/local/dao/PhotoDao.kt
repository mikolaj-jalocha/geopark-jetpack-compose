package com.geopark.feature_locations_events.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.geopark.feature_locations_events.data.local.entity.PhotoEntity

@Dao
interface PhotoDao : BaseDao<PhotoEntity> {

    @Query("SELECT * FROM photoentity")
    suspend fun getPhotos() : List<PhotoEntity>

    @Query("SELECT * FROM photoentity WHERE photoId=:photoId")
    suspend fun getPhotoById(photoId : String) : PhotoEntity?

}