package com.geopark.feature_locations_events.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationCategoryCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationLabelCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationPhotoCrossRef
import com.geopark.feature_locations_events.data.local.bridge.location_bridge.LocationTagCrossRef
import com.geopark.feature_locations_events.data.local.entity.*

data class Location(
    @Embedded val location: LocationEntity = LocationEntity(),
    @Relation(
        parentColumn = "locationId",
        entityColumn = "labelId",
        associateBy = Junction(LocationLabelCrossRef::class)
    )
    val labels: List<LabelEntity> = emptyList(),

    @Relation(
        parentColumn = "locationId",
        entityColumn = "categoryId",
        associateBy = Junction(LocationCategoryCrossRef::class)
    )
    val categories: List<CategoryEntity> = emptyList(),

    @Relation(
        parentColumn = "locationId",
        entityColumn = "photoId",
        associateBy = Junction(LocationPhotoCrossRef::class)
    )
    val photos: List<PhotoEntity> = emptyList(),

    @Relation(
        parentColumn = "locationId",
        entityColumn = "tagId",
        associateBy = Junction(LocationTagCrossRef::class)
    )
    val tags: List<TagEntity> = emptyList(),


    )