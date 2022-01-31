package com.geopark.feature_locations_events.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity
data class LocationEntity(
    @PrimaryKey(autoGenerate = false)
    val locationId: String = "",
    val name: String = "",
    val address: String = "",
    val description: String = "",
    val website: String = "",
    val telephone : String = "",
    val latitude: String = "",
    val longitude: String = ""
)