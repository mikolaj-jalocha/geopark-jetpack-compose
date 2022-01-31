package com.geopark.feature_locations_events.data.local.bridge.event_bridge

import androidx.room.Entity

@Entity(primaryKeys = ["eventId", "categoryId"])
data class EventCategoryCrossRef(
    val eventId: String,
    val categoryId: String
)