package com.geopark.feature_locations_events.data.local

import androidx.room.*
import com.geopark.feature_locations_events.domain.model.Event
import com.geopark.feature_locations_events.domain.model.Location

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations")
    suspend fun getLocations(): List<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<Location>)

    @Query("SELECT * FROM locations WHERE  name=:name")
    suspend fun getLocationByName(name: String): Location?
}