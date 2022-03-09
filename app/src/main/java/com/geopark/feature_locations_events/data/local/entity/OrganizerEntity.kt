package com.geopark.feature_locations_events.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrganizerEntity(
    @PrimaryKey
    val organizerId: String = "",
    val name: String = "",
    val email: String = "",
    val telephone: String = "",
    val photo: String = "",
    val website: String = "",
    val description: String = ""
)