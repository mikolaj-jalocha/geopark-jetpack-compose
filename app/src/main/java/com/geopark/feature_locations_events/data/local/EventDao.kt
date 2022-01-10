package com.geopark.feature_locations_events.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geopark.feature_locations_events.domain.model.Event
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    suspend fun getEvents() : List<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events : List<Event>)


    @Query("SELECT DISTINCT promoterName FROM events")
    fun getEventsLocations() : Flow<List<String>>
}