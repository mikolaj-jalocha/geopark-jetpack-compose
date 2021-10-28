package com.geopark.feature_locations.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geopark.feature_locations.domain.model.Location

@Database(
    entities = [Location::class],
    version = 1
)
abstract class LocationDatabase : RoomDatabase() {

    abstract val locationDao : LocationDao


    companion object {
        const val  DATABASE_NAME = "locations_db"
    }
}