package com.geopark.feature_locations.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.geopark.di.AppModule
import com.geopark.feature_locations.domain.model.Location
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [Location::class],
    version = 1
)
abstract class LocationDatabase : RoomDatabase() {
    abstract val locationDao: LocationDao
   }



