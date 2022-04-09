package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.source.FakeEventRepository
import com.geopark.feature_locations_events.data.util.EventDate
import com.geopark.feature_locations_events.domain.util.EventCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetEventsForDateUseCaseTest {

    private lateinit var getEventsForCategoryAndDateUseCase: GetEventsForDateUseCase
    private lateinit var repository: FakeEventRepository
    private lateinit var eventDates: List<EventDate>
    lateinit var categories: List<EventCategory>

    @Before
    fun setupUseCase() {
        repository = FakeEventRepository()
        val getAllUseCase = GetAllEventsFlowUseCase(repository)
        val categoryUseCase = GetEventsForCategoryUseCase(getAllUseCase)
        getEventsForCategoryAndDateUseCase = GetEventsForDateUseCase(categoryUseCase)
        eventDates = repository.dates
        categories = repository.eventCategories.sorted()
    }


    @Test
    fun `returned events match given date`() = runBlocking {
        EventCategory.values().forEach { expectedCategory ->
            eventDates.forEach { expectedDate ->
                val filteredData = getEventsForCategoryAndDateUseCase(
                    eventCategory = expectedCategory,
                    date = expectedDate.getLocalDate()
                ).first().data

                filteredData.forEach { event ->
                    val actualDates = event.event.eventDate
                    assert(actualDates.contains(expectedDate))

                }
            }
        }
    }

    @Test
    fun `returned events match given category`() = runBlocking {
        categories.forEach { expectedCategory ->
            eventDates.forEach { expectedDate ->
                val filteredData = getEventsForCategoryAndDateUseCase(
                    eventCategory = expectedCategory,
                    date = expectedDate.getLocalDate()
                ).first().data

                filteredData.forEach { event ->
                    val actualCategories = event.categories.map { it.categoryId }
                    assert(actualCategories.contains(expectedCategory.categoryId))
                }
            }
        }
    }

    @Test
    fun `returned Result type is 'Loading' `() = runBlocking {

        repository.returnLoadingState()

        val actualReturnType = getEventsForCategoryAndDateUseCase(EventCategory.ALL,eventDates[0].getLocalDate()).first()

        assert(actualReturnType is Resource.Loading)
    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val successResource = getEventsForCategoryAndDateUseCase(EventCategory.ALL,eventDates[0].getLocalDate()).first()

        assert(successResource is Resource.Success)

    }

}