package com.geopark.feature_locations_events.data.source.dao


import com.geopark.feature_locations_events.data.local.bridge.event_bridge.*
import com.geopark.feature_locations_events.data.local.dao.EventDao
import com.geopark.feature_locations_events.data.local.entity.EventEntity
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.local.model.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeEventDao : EventDao {

    private val data = mutableListOf<Event>()

    fun insertEvents(events: List<Event>) {
        data.clear()
        data.addAll(events)
    }


    override suspend fun getEvents(): List<Event> {
        TODO("Not yet implemented")
    }

    override fun getEventsFlow(): Flow<List<Event>> {
            return  flow {
                emit(data)
            }
    }

    override suspend fun insertEventCategory(eventCategory: EventCategoryCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun insertEventLocation(eventLocation: EventLocationCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun insertEventLabel(eventLabel: EventLabelCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun insertEventTag(eventTag: EventTagCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun insertEventPhoto(eventPhoto: EventPhotoCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun insertEventEntity(eventEntity: EventEntity) {
        TODO("Not yet implemented")
    }
}