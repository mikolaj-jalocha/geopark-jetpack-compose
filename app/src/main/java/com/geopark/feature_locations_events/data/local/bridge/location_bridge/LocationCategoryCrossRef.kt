package com.geopark.feature_locations_events.data.local.bridge.location_bridge

import androidx.room.Entity


@Entity(primaryKeys = ["locationId","categoryId"])
data class LocationCategoryCrossRef(
    val locationId : String,
    val categoryId : String
)
