package com.geopark.feature_locations_events.data.repository

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.entity.EventEntity
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.source.FakeEventDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test

class EventRepositoryImplTest {

    private val dao = FakeEventDao()
    private val eventRepository = EventRepositoryImpl(dao)

    @Test
    fun `when result is empty Resource Loading is returned`() = runBlocking {
        val resource = eventRepository.getEventsFlow().first()
        assert(resource.data.isEmpty() && resource is Resource.Loading)
    }


    @Test
    fun `when result is not empty Resource Success is returned`() = runBlocking {
        insertData()
        val resource = eventRepository.getEventsFlow().first()
        assert(resource.data.isNotEmpty() && resource is Resource.Success)
    }


    private fun insertData() {
        val data = listOf<Event>(
            Event(
                event = EventEntity()
            ),
            Event(event = EventEntity()),
            Event(
                event = EventEntity()
            )
        )
        dao.insertEvents(data)
    }

}