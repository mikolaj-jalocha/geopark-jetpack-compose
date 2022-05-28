package com.geopark.feature_locations_events.data.repository

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.entity.EventEntity
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.source.dao.FakeEventDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class EventRepositoryImplTest {

    private val dao = FakeEventDao()
    private val eventRepository = EventRepositoryImpl(dao)

    @Test
    fun `when result is empty Resource Loading is returned`() = runBlocking {
        val resource = eventRepository.getEventsFlow().first()

        assertThat(resource.data).isEmpty()
        assertThat(resource is Resource.Loading).isTrue()
    }


    @Test
    fun `when result is not empty Resource Success is returned`() = runBlocking {
        insertData()
        val resource = eventRepository.getEventsFlow().first()

        assertThat(resource.data).isNotEmpty()
        assertThat(resource is Resource.Success).isTrue()
    }


    private fun insertData() {
        val data = listOf(
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