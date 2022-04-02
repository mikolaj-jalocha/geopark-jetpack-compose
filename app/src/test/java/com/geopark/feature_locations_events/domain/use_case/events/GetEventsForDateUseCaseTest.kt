package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.source.FakeEventRepository
import com.geopark.feature_locations_events.data.util.EventDate
import com.geopark.feature_locations_events.domain.util.EventCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetEventsForDateUseCaseTest{

   private lateinit var forDateUseCase: GetEventsForDateUseCase
    private lateinit var categoryUseCase: GetEventsForCategoryUseCase
    private lateinit var getAllUseCase: GetAllEventsFlowUseCase
    private lateinit var repository: FakeEventRepository
    private lateinit var eventDates : List<EventDate>
    lateinit var categories : List<EventCategory>

    @Before
    fun setupUseCase() {
        repository = FakeEventRepository()
        getAllUseCase = GetAllEventsFlowUseCase(repository)
        categoryUseCase = GetEventsForCategoryUseCase(getAllUseCase)
        forDateUseCase = GetEventsForDateUseCase(categoryUseCase)
        eventDates = repository.datesForEvents
        val allCategories = EventCategory.values().toMutableList()
        allCategories.remove(EventCategory.ALL)
        categories = allCategories.sorted()
    }


    @Test
    fun returnEventsForDateAndCategory() = runBlocking {

        categories.forEach { category ->
            eventDates.forEach { date ->
                forDateUseCase(category,date.getLocalDate()).first().data.onEach { event ->
                    assert(event.event.eventDate.contains(date) && event.categories.map { it.categoryId }.contains(category.categoryId))
                }
            }
        }

    }

    @Test
    fun returnsEventsForDate() = runBlocking {

        eventDates.forEach { eventDate ->
            forDateUseCase(EventCategory.ALL,eventDate.getLocalDate()).first().data.onEach {
                assert(it.event.eventDate.contains(eventDate))
            }

        }

    }

    @Test
    fun returnSameResultTypeAsInInput() = runBlocking {

        repository.returnLoadingState()
        val loadingResource = forDateUseCase(EventCategory.ALL, LocalDate.now()).first()

        assert(loadingResource is Resource.Loading)

        repository.returnSuccessState()

        val successResource = forDateUseCase(EventCategory.ALL, LocalDate.now()).first()

        assert(successResource is Resource.Success)

    }

}