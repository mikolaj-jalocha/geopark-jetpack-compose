package com.geopark.feature_locations_events.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.geopark.feature_locations_events.data.util.EventDate

@Entity
data class EventEntity(
    @PrimaryKey val eventId: String = "",
    val eventTitle: String ="",
    val eventDescription: String = "",
    val eventOrganizerId: String = "",
    val eventDate: List<EventDate> = emptyList()
)



