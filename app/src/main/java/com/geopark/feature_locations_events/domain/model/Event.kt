package com.geopark.feature_locations_events.domain.model

import androidx.room.Entity
import com.geopark.feature_locations_events.domain.util.EventCategory

@Entity(tableName = "events", primaryKeys = ["title","date","startHour"])
data class Event(
    val date : String,
    val startHour : String,
    val endHour : String,
    val photoPath : String,
    val title : String,
    val price  : String,
    val mapLocation : String,
    val promoterName : String,
    val description : String,
    val category : List<EventCategory>
)
