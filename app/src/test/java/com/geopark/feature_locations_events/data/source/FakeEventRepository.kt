package com.geopark.feature_locations_events.data.source

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.entity.CategoryEntity
import com.geopark.feature_locations_events.data.local.entity.EventEntity
import com.geopark.feature_locations_events.data.local.model.Event
import com.geopark.feature_locations_events.data.util.EventDate
import com.geopark.feature_locations_events.domain.repository.EventRepository
import com.geopark.feature_locations_events.domain.util.EventCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeEventRepository : EventRepository {




    private val categories = mutableListOf<CategoryEntity>()
    private val dates = mutableListOf<EventDate>()

    val datesForEvents = dates.toList()


    init {
        EventCategory.values().forEach {
            if (it.categoryId != EventCategory.ALL.categoryId)
                categories.add(CategoryEntity(it.categoryId, it.categoryName))
        }

        for (i in 1..15)
            dates.add(EventDate("","","$i-04-2022"))

    }


    private val data = listOf<Event>(
        Event(
            event = EventEntity(eventDate = listOf(dates[0],dates[1],dates[2])), categories = listOf(categories[1],categories[2])
        ),
        Event(
            event = EventEntity(eventDate = listOf(dates[3])), categories = listOf(categories[3],categories[4])
        ),
        Event(
            event = EventEntity(eventDate = listOf(dates[4],dates[5],dates[0],dates[7])), categories = listOf(categories[5],categories[6])
        )
    )

    private var isLoading = false


    fun returnLoadingState() {
        isLoading = true
    }

    fun returnSuccessState() {
        isLoading = false
    }

    override fun getEventsFlow() = flow {
        if (isLoading)
            emit(Resource.Loading<List<Event>>(data = data))
        else
            emit(Resource.Success<List<Event>>(data = data))
    }


    override fun getEvents(): Flow<Resource<List<Event>>> {
        TODO("Not yet implemented")
    }
}