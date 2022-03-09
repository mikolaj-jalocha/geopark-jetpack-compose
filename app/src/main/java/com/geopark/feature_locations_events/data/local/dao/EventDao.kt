package com.geopark.feature_locations_events.data.local.dao

import android.util.Log
import androidx.room.*
import com.geopark.feature_locations_events.data.local.bridge.event_bridge.*
import com.geopark.feature_locations_events.data.local.entity.EventEntity
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.remote.dto.EventDto
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Transaction
    @Query("SELECT * FROM EventEntity")
    suspend fun getEvents(): List<Event>

    @Transaction
    @Query("SELECT * FROM EventEntity")
    fun getEventsFlow() : Flow<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventCategory(eventCategory: EventCategoryCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventLocation(eventLocation: EventLocationCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventLabel(eventLabel: EventLabelCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventTag(eventTag: EventTagCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventPhoto(eventPhoto: EventPhotoCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventEntity(eventEntity: EventEntity)


    @Transaction
    suspend fun addEvents(
        eventDto: List<EventDto>
    ) {

        eventDto.forEach {
            it.apply {
                insertEventEntity(
                    EventEntity(
                        eventId,
                        eventTitle,
                        eventDescription,
                        eventOrganizerId,
                        eventDate.sorted()
                    )
                )
                locationsIds.forEach { id ->
                    insertEventLocation(EventLocationCrossRef(eventId, id))
                }
                labelsIds.forEach { id ->
                    insertEventLabel(EventLabelCrossRef(eventId, id))
                }
                categoriesIds.forEach { id ->
                    insertEventCategory(EventCategoryCrossRef(eventId, id))
                }
                tagsIds.forEach { id ->
                    insertEventTag(EventTagCrossRef(eventId, id))
                }
                photosIds.forEach { id ->
                    insertEventPhoto(EventPhotoCrossRef(eventId, id))
                }

            }
        }
    }

}