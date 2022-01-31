package com.geopark.feature_locations_events.data.local.dao

import androidx.room.*
import com.geopark.feature_locations_events.data.local.bridge.event_bridge.*
import com.geopark.feature_locations_events.data.local.entity.EventEntity
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.remote.dto.EventDto

@Dao
interface EventDao {

    @Transaction
    @Query("SELECT * FROM EventEntity")
    suspend fun getEvents(): List<Event>

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
    suspend fun addEvent(
        eventDto: EventDto
    ) {

        eventDto.apply {

            insertEventEntity(EventEntity(eventId, eventTitle,eventDescription, eventDate,eventOrganizerId))
            locationsIds.forEach {
                insertEventLocation(EventLocationCrossRef(eventId, it))
            }
            labelsIds.forEach {
                insertEventLabel(EventLabelCrossRef(eventId, it))
            }
            categoriesIds.forEach {
                insertEventCategory(EventCategoryCrossRef(eventId, it))
            }
            tagsIds.forEach {
                insertEventTag(EventTagCrossRef(eventId, it))
            }
            photosIds.forEach {
                insertEventPhoto(EventPhotoCrossRef(eventId, it))
            }


        }
    }

}