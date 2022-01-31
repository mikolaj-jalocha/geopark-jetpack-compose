package com.geopark.feature_locations_events.data.local.bridge.location_bridge

import androidx.room.Entity

@Entity(primaryKeys = ["locationId","tagId"])
data class LocationTagCrossRef(
    val locationId : String,
    val tagId : String
)
