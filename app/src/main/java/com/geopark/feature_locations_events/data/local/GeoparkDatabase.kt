package com.geopark.feature_locations_events.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geopark.feature_locations_events.data.local.bridge.event_bridge.*
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationCategoryCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationLabelCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationPhotoCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationTagCrossRef
import com.geopark.feature_locations_events.data.local.dao.*
import com.geopark.feature_locations_events.data.local.entity.*

@Database(
    entities = [EventEntity::class, PhotoEntity::class, TagEntity::class, LocationEntity::class, LabelEntity::class, OrganizerEntity::class, CategoryEntity::class,
        EventLocationCrossRef::class, EventLabelCrossRef::class, EventCategoryCrossRef::class, EventPhotoCrossRef::class, EventTagCrossRef::class,
        LocationLabelCrossRef::class, LocationTagCrossRef::class, LocationPhotoCrossRef::class, LocationCategoryCrossRef::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class GeoparkDatabase : RoomDatabase() {

    abstract val eventDao: EventDao
    abstract val labelDao: LabelDao
    abstract val locationDao: LocationDao
    abstract val organizerDao: OrganizerDao
    abstract val tagDao: TagDao
    abstract val photoDao: PhotoDao
    abstract val categoryDao : CategoryDao

}
