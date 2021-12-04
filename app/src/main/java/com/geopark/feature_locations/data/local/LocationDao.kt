package com.geopark.feature_locations.data.local

import androidx.room.*
import com.geopark.feature_locations.domain.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    fun getLocations() : Flow<List<Location>>

    @Query("SELECT * FROM location WHERE name = :name")
    suspend fun getLocationByName(name : String) : Location?

    @Update
    suspend fun updateLocation(location: Location)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Delete
    suspend fun deleteLocation(location: Location)


}