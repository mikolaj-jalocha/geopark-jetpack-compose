package com.geopark.feature_locations_events.data.remote.dto


data class EventDto(
    val eventId: String,
    val eventTitle: String,
    val eventDescription: String,
    val eventDate: String,
    val eventOrganizerId : String,

    val categoriesIds: List<String>,
    val labelsIds: List<String>,
    val locationsIds: List<String>,
    val organizerId: String,
    val photosIds: List<String>,
    val tagsIds: List<String>
)