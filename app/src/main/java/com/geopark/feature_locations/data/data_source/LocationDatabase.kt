package com.geopark.feature_locations.data.data_source

import android.util.Log
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

    abstract val locationDao : LocationDao


    companion object {
        const val  DATABASE_NAME = "locations_db"
    }

    class Callback @Inject constructor(
        private val database: Provider<LocationDatabase>,
        @AppModule.ApplicationScope private val applicationScope : CoroutineScope,
        @AppModule.LocationsQuery private val locationsQuery : Query
    ) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().locationDao

            applicationScope.launch {
                for (location in locationsQuery.get().await().map { document ->
                    document.toObject(Location::class.java)
                }) {
                    dao.insertLocation(location)
                }
            }
        }
    }
}