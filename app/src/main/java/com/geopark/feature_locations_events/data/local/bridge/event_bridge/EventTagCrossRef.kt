package com.geopark.feature_locations_events.data.local.bridge.event_bridge

import androidx.room.Entity


@Entity(primaryKeys = ["eventId","tagId"])
data class EventTagCrossRef(
    val eventId : String,
    val tagId : String
)
