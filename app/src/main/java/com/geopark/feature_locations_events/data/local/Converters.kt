package com.geopark.feature_locations_events.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.geopark.feature_locations_events.data.util.EventDate
import com.geopark.feature_locations_events.data.util.JsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromEventDateJson(json: String): List<EventDate> {
        return jsonParser.fromJson<List<EventDate>>(
            json,
            object : TypeToken<ArrayList<EventDate>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toEventDateJson(eventDate: List<EventDate>): String {
        return jsonParser.toJson(
            eventDate,
            object : TypeToken<ArrayList<EventDate>>() {}.type
        ) ?: "[]"
    }
}