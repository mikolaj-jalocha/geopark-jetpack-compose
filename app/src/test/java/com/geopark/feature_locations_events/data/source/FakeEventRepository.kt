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


    private val categories: List<CategoryEntity>
    val eventCategories : List<EventCategory>
    val dates: List<EventDate>


    init {



        val allCategories = EventCategory.values().toMutableList()
        allCategories.remove(EventCategory.ALL)

        eventCategories = allCategories.toList()

        val mutableCategories = mutableListOf<CategoryEntity>()
        allCategories.forEach {
            mutableCategories.add(CategoryEntity(it.categoryId, it.categoryName))
        }


        categories = mutableCategories.toList()

        val mutableDates = mutableListOf<EventDate>()

        for (i in 10..25)
            mutableDates.add(EventDate("", "", "2022-04-$i"))

        dates = mutableDates.toList()
    }


    private val data = listOf<Event>(
        Event(
            event = EventEntity(eventDate = listOf(dates[0], dates[1], dates[2])),
            categories = listOf(categories[1], categories[2])
        ),
        Event(
            event = EventEntity(eventDate = listOf(dates[3])),
            categories = listOf(categories[3], categories[4])
        ),
        Event(
            event = EventEntity(eventDate = listOf(dates[4], dates[5], dates[0], dates[7])),
            categories = listOf(categories[5], categories[6])
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


}