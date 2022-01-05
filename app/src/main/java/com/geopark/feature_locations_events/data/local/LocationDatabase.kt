package com.geopark.feature_locations_events.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geopark.feature_locations_events.domain.model.Event
import com.geopark.feature_locations_events.domain.model.Location

@Database(
    entities = [Location::class, Event::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class LocationDatabase : RoomDatabase() {
    abstract val locationDao: LocationDao
    abstract val eventDao : EventDao
   }



