package com.geopark.feature_locations.data.local

import androidx.room.*
import com.geopark.feature_locations.domain.model.Location

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    suspend fun getLocations(): List<Location>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocations(locations: List<Location>)

    @Query("SELECT * FROM location WHERE name = :name")
    suspend fun getLocationByName(name: String): Location?

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateLocation(location: Location)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)


}