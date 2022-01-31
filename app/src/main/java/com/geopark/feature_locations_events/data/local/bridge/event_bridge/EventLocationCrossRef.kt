package com.geopark.feature_locations_events.data.local.bridge.event_bridge

import androidx.room.Entity

@Entity(primaryKeys = ["eventId", "locationId"])
data class EventLocationCrossRef(
    val eventId: String,
    val locationId: String
)

