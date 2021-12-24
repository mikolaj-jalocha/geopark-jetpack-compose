package com.geopark.feature_locations_events.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geopark.feature_locations_events.domain.model.Location

@Database(
    entities = [Location::class],
    version = 1
)
abstract class LocationDatabase : RoomDatabase() {
    abstract val locationDao: LocationDao
   }


