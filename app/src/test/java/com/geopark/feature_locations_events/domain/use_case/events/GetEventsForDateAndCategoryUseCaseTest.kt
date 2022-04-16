package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.source.FakeEventRepository
import com.geopark.feature_locations_events.data.util.EventDate
import com.geopark.feature_locations_events.domain.util.EventCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetEventsForDateAndCategoryUseCaseTest {
    private val repository: FakeEventRepository = FakeEventRepository()
    private val getEventsForCategoryAndDateAndCategoryUseCase: GetEventsForDateAndCategoryUseCase =
        GetEventsForDateAndCategoryUseCase(
            GetEventsForCategoryUseCase(
                GetAllEventsFlowUseCase(
                    repository
                )
            )
        )
    private val eventDates: List<EventDate> = repository.dates


    @Test
    fun `returned events match given date`() = runBlocking {
        eventDates.forEach { expectedDate ->
            val filteredData = getEventsForCategoryAndDateAndCategoryUseCase(
                eventCategory = EventCategory.ALL,
                date = expectedDate.getLocalDate()
            ).first().data

            filteredData.forEach { event ->
                val actualDates = event.event.eventDate
                assert(actualDates.contains(expectedDate))

            }
        }
    }

    @Test
    fun `returned Result type is 'Loading' `() = runBlocking {

        repository.returnLoadingState()

        val actualReturnType = getEventsForCategoryAndDateAndCategoryUseCase(
            EventCategory.ALL,
            eventDates[0].getLocalDate()
        ).first()

        assert(actualReturnType is Resource.Loading)
    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val successResource = getEventsForCategoryAndDateAndCategoryUseCase(
            EventCategory.ALL,
            eventDates[0].getLocalDate()
        ).first()

        assert(successResource is Resource.Success)

    }

}