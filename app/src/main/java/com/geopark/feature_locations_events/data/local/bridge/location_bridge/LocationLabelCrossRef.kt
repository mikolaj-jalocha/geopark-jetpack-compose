package com.geopark.feature_locations_events.data.local.bridge.location_bridge

import androidx.room.Entity


@Entity(primaryKeys = ["locationId", "labelId"])
data class LocationLabelCrossRef(
    val locationId: String,
    val labelId: String
)
