package com.geopark.feature_locations_events.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LabelEntity(
    @PrimaryKey
    val labelId: String,
    val name: String
)