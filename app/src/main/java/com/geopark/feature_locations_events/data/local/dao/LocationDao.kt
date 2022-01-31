package com.geopark.feature_locations_events.data.local.dao

import androidx.room.*
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationCategoryCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationLabelCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationPhotoCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationTagCrossRef
import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.data.remote.dto.LocationDto


@Dao
interface LocationDao : BaseDao<LocationEntity> {

    @Transaction
    @Query("SELECT * FROM LocationEntity")
    suspend fun getLocations(): List<Location>

    @Transaction
    @Query("SELECT * FROM LocationEntity WHERE locationId=:locationId")
    suspend fun getLocationById(locationId: String): Location?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationEntity(locationEntity: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationLabel(locationLabel: LocationLabelCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationCategory(locationCategory: LocationCategoryCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationPhoto(locationPhoto: LocationPhotoCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationTag(locationTag: LocationTagCrossRef)

    @Transaction
    suspend fun addLocation(locationDto: LocationDto) {
        locationDto.apply {
            insertLocationEntity(
                LocationEntity(
                    locationId,
                    name,
                    address,
                    description,
                    website,
                    telephone,
                    latitude,
                    longitude
                )
            )
            labelsIds.forEach {
                insertLocationLabel(LocationLabelCrossRef(locationId, it))
            }
            categoriesIds.forEach {
                insertLocationCategory(LocationCategoryCrossRef(locationId, it))
            }
            photosIds.forEach {
                insertLocationPhoto(LocationPhotoCrossRef(locationId, it))
            }
            tagsIds.forEach {
                insertLocationTag(LocationTagCrossRef(locationId, it))
            }
        }
    }


}