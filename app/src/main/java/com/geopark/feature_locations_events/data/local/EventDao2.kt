package com.geopark.feature_locations_events.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.geopark.feature_locations_events.data.local.bridge.event_bridge.EventCategoryCrossRef
import com.geopark.feature_locations_events.data.local.bridge.event_bridge.EventLabelCrossRef
import com.geopark.feature_locations_events.data.local.bridge.event_bridge.EventLocationCrossRef
import com.geopark.feature_locations_events.data.local.entity.*
import com.geopark.feature_locations_events.data.local.model.Event


@Dao
interface EventDao2 {

    @Transaction
    @Query("SELECT * FROM EventEntity")
    suspend fun getEventWithLocations(): List<Event>

    @Insert(onConflict = REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertLabel(label: LabelEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertOrganizer(organizer : OrganizerEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertEventCategory(eventCategory: EventCategoryCrossRef)

    @Insert(onConflict = REPLACE)
    suspend fun insertEventLocation(eventLocation: EventLocationCrossRef)

    @Insert(onConflict = REPLACE)
    suspend fun insertEventLabel(eventLabel: EventLabelCrossRef)
}