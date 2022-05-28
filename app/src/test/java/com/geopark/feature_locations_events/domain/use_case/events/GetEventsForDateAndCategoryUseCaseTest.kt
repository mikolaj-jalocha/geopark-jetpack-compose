package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.source.FakeEventRepository
import com.geopark.feature_locations_events.data.util.EventDate
import com.geopark.feature_locations_events.domain.util.EventCategory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class GetEventsForDateAndCategoryUseCaseTest {

    private lateinit var repository: FakeEventRepository
    private lateinit var getEventsForCategoryAndDateAndCategoryUseCase: GetEventsForDateAndCategoryUseCase
    private lateinit var eventDates: List<EventDate>

    @Before
    fun setupUseCase() {
        repository = FakeEventRepository()
        getEventsForCategoryAndDateAndCategoryUseCase =
            GetEventsForDateAndCategoryUseCase(
                GetEventsForCategoryUseCase(
                    GetAllEventsFlowUseCase(
                        repository
                    )
                )
            )
        eventDates = repository.dates
    }


    @Test
    fun `returned events match given date`() = runBlocking {
        eventDates.forEach { expectedDate ->
            val filteredData = getEventsForCategoryAndDateAndCategoryUseCase(
                eventCategory = EventCategory.ALL,
                date = expectedDate.getLocalDate()
            ).first().data

            filteredData.forEach { event ->
                val actualDates = event.event.eventDate
                assertThat(actualDates).contains(expectedDate)

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

        assertThat(actualReturnType is Resource.Loading).isTrue()
    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val actualReturnType = getEventsForCategoryAndDateAndCategoryUseCase(
            EventCategory.ALL,
            eventDates[0].getLocalDate()
        ).first()

        assertThat(actualReturnType is Resource.Success).isTrue()

    }

}