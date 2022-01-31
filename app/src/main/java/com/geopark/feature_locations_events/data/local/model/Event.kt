package com.geopark.feature_locations_events.data.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.geopark.feature_locations_events.data.local.bridge.event_bridge.*
import com.geopark.feature_locations_events.data.local.entity.*


data class Event(
    @Embedded val event: EventEntity,

    @Relation(
        entity = OrganizerEntity::class,
        parentColumn = "eventOrganizerId",
        entityColumn = "organizerId"
    )
    val organizer: OrganizerEntity,

    @Relation(
        entity = LocationEntity::class,
        parentColumn = "eventId",
        entityColumn = "locationId",
        associateBy = Junction(EventLocationCrossRef::class)
    )
    val locations: List<Location>,

    @Relation(
        parentColumn = "eventId",
        entityColumn = "labelId",
        associateBy = Junction(EventLabelCrossRef::class)
    )
    val labels: List<LabelEntity>,
    @Relation(
        parentColumn = "eventId",
        entityColumn = "categoryId",
        associateBy = Junction(EventCategoryCrossRef::class)
    )
    val categories: List<CategoryEntity>,
    @Relation(
        parentColumn = "eventId",
        entityColumn = "tagId",
        associateBy = Junction(EventTagCrossRef::class)
    )
    val tags: List<TagEntity>,

    @Relation(
        parentColumn = "eventId",
        entityColumn = "photoId",
        associateBy = Junction(EventPhotoCrossRef::class)
    )
    val photos: List<PhotoEntity>


)






