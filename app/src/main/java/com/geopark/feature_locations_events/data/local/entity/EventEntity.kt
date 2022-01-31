package com.geopark.feature_locations_events.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity(
    @PrimaryKey val eventId: String,
    val eventTitle: String,
    val eventDescription: String,
    val eventDate: String,
    val eventOrganizerId : String
)