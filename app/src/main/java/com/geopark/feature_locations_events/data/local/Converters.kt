package com.geopark.feature_locations_events.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.geopark.feature_locations_events.data.util.JsonParser
import com.geopark.feature_locations_events.domain.model.Event
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromCategoriesJson(json: String): List<String> {
        return jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toCategoriesJson(categories: List<String>): String {
        return jsonParser.toJson(categories,
            object : TypeToken<ArrayList<String>>(){}.type) ?: "[]"
    }
}