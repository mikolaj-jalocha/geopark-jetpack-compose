package com.geopark.feature_locations_events.domain.use_case.events

import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.entity.CategoryEntity
import com.geopark.feature_locations_events.data.source.FakeEventRepository
import com.geopark.feature_locations_events.domain.util.EventCategory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetEventsForCategoryUseCaseTest {


    private val repository: FakeEventRepository = FakeEventRepository()
    private val getAllUseCase : GetAllEventsFlowUseCase= GetAllEventsFlowUseCase(repository)
    private val categoryUseCase: GetEventsForCategoryUseCase =
        GetEventsForCategoryUseCase(getAllUseCase)
    private val categories: List<EventCategory> = repository.eventCategories.sorted()




    @Before
    fun setupUseCase() {

    }

    @Test
    fun `returned events match given category`() = runBlocking {

        categories.forEach { expectedCategory ->
            val filteredData = categoryUseCase(expectedCategory).first().data
            filteredData.onEach { event ->

                val actualCategories = event.categories.map { it.categoryId }
                assert(actualCategories.contains(expectedCategory.categoryId))

            }
        }
    }


    @Test
    fun `events returned for ALL category are all events that exist`() = runBlocking {

        val actualValue = categoryUseCase(EventCategory.ALL).first().data
        val expectedValue = getAllUseCase().first().data

        assert(actualValue == expectedValue)
    }


    @Test
    fun `returned Result type is 'Loading' `() = runBlocking {

        repository.returnLoadingState()

        val actualReturnType = categoryUseCase(EventCategory.ALL).first()

        assert(actualReturnType is Resource.Loading)
    }

    @Test
    fun `returned Result type is 'Success'`() = runBlocking {
        repository.returnSuccessState()
        val successResource = categoryUseCase(EventCategory.ALL).first()

        assert(successResource is Resource.Success)

    }


}