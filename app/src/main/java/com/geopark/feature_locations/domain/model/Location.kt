package com.geopark.feature_locations.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude@Entity
data class Location(
    @PrimaryKey
    val name: String =

 "",
    val type: String = "",
    val location: String = "",
    val description: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val website: String = "",
    val photo : String = "",

    @Exclude
    val isFavorite : Boolean = false,
    @Exclude
    val wasRecentlyWatched : Boolean = false
)


