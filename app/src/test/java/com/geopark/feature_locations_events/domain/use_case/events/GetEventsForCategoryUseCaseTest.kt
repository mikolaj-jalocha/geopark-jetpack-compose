package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.source.FakeEventRepository
import com.geopark.feature_locations_events.domain.util.EventCategory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetEventsForCategoryUseCaseTest {


    private lateinit var repository: FakeEventRepository
    private lateinit var  getAllUseCase : GetAllEventsFlowUseCase
    private lateinit var categoryUseCase: GetEventsForCategoryUseCase
    private lateinit var  categories: List<EventCategory>

    @Before
    fun setupUseCase() {
        repository = FakeEventRepository()
        getAllUseCase = GetAllEventsFlowUseCase(repository)
        categoryUseCase = GetEventsForCategoryUseCase(getAllUseCase)
        categories =  repository.eventCategories.sorted()
    }

    @Test
    fun `returned events match given category`() = runBlocking {

        categories.forEach { expectedCategory ->
            val filteredData = categoryUseCase(expectedCategory).first().data
            filteredData.onEach { event ->

                val actualCategories = event.categories.map { it.categoryId }

                assertThat(actualCategories).contains(expectedCategory.categoryId)

               // assert(actualCategories.contains(expectedCategory.categoryId))

            }
        }
    }

    @Test
    fun `events returned for ALL category are all events that exist`() = runBlocking {

        val actualValue = categoryUseCase(EventCategory.ALL).first().data
        val expectedValue = getAllUseCase().first().data

        assertThat(actualValue).isEqualTo(expectedValue)

        //assert(actualValue == expectedValue)
    }


    @Test
    fun `returned Result type is 'Loading' `() = runBlocking {

        repository.returnLoadingState()

        val actualReturnType = categoryUseCase(EventCategory.ALL).first()

        assertThat(actualReturnType is Resource.Loading).isTrue()

    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val actualReturnType = categoryUseCase(EventCategory.ALL).first()
        assertThat(actualReturnType is Resource.Success).isTrue()

    }


}