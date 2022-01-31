package com.geopark.feature_locations_events.data.local.bridge.event_bridge

import androidx.room.Entity

@Entity(primaryKeys = ["eventId", "labelId"])
data class EventLabelCrossRef(
    val eventId: String,
    val labelId: String
)