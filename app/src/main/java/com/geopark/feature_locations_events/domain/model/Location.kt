package com.geopark.feature_locations_events.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey
    val name: String = "",
    val type: String = "",
    val location: String = "",
    val description: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val website: String = "",
    val photo : String = "",

)


