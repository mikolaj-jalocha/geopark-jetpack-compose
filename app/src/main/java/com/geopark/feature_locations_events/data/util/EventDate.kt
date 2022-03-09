package com.geopark.feature_locations_events.data.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class EventDate(
    val startHour: String = "",
    val endHour: String = "",
    val startDate: String = ""
) : Comparable<EventDate> {
    override fun compareTo(other: EventDate) = startDate.compareTo(other.startDate)
    override fun equals(other: Any?): Boolean {
        return if (other is EventDate)
            return other.startDate == this.startDate
        else
            false
    }

    fun getLocalDate() = LocalDate.parse(startDate,DateTimeFormatter.ISO_LOCAL_DATE)
}