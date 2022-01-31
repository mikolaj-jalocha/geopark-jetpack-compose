package com.geopark.feature_locations_events.data.local.bridge.event_bridge

import androidx.room.Entity

@Entity(primaryKeys = ["eventId","photoId"])
data class EventPhotoCrossRef(
    val eventId : String,
    val photoId : String
)
