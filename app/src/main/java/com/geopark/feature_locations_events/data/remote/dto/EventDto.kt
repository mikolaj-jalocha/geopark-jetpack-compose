package com.geopark.feature_locations_events.data.remote.dto

import com.geopark.feature_locations_events.data.util.EventDate


data class EventDto(
    val eventId: String,
    val eventTitle: String,
    val eventDescription: String,
    val eventOrganizerId : String,
    val categoriesIds: List<String>,
    val labelsIds: List<String>,
    val locationsIds: List<String>,
    val organizerId: String,
    val photosIds: List<String>,
    val tagsIds: List<String>,
    val eventDate : List<EventDate>
)