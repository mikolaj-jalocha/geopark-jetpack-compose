package com.geopark.feature_locations_events.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.geopark.feature_locations_events.data.util.JsonParser
import com.geopark.feature_locations_events.domain.util.EventCategory
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromCategoriesJson(json: String): List<EventCategory> {
        val list = jsonParser.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>(){}.type
        ) ?: emptyList()

        return list.map { EventCategory.valueOf(it)}
    }


     @TypeConverter
   fun toCategoriesJson(categories: List<EventCategory>): String {
       return jsonParser.toJson(categories.map { it.name },
           object : TypeToken<ArrayList<String>>(){}.type) ?: "[]"
   }

   /* @TypeConverter
    fun toCategoriesJson(categories: List<String>): String {
        return jsonParser.toJson(categories,
            object : TypeToken<ArrayList<String>>(){}.type) ?: "[]"
    }*/
}