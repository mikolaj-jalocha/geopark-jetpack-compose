package com.geopark.feature_locations_events.data.local.bridge.location_bridge

import androidx.room.Entity

@Entity(primaryKeys = ["locationId", "photoId"])
data class LocationPhotoCrossRef(
    val locationId: String,
    val photoId: String
)
