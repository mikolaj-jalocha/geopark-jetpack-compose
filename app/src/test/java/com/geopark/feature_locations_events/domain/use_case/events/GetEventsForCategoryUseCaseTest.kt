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


    lateinit var categoryUseCase: GetEventsForCategoryUseCase
    lateinit var getAllUseCase: GetAllEventsFlowUseCase
    private lateinit var repository: FakeEventRepository
    lateinit var categories : List<EventCategory>

    @Before
    fun setupUseCase() {
        repository = FakeEventRepository()
        getAllUseCase = GetAllEventsFlowUseCase(repository)
        categoryUseCase = GetEventsForCategoryUseCase(getAllUseCase)
        val allCategories = EventCategory.values().toMutableList()
        allCategories.remove(EventCategory.ALL)
        categories = allCategories.sorted()
    }

    @Test
    fun returnsEventForCategory() = runBlocking {


        val allData = getAllUseCase().first().data

        // get  ALL events
        assert(categoryUseCase(EventCategory.ALL).first().data.size == allData.size)

        // other categories

        categories.forEach { category ->
            val filteredData = categoryUseCase(category).first().data
                filteredData.onEach { event ->
                    assert(
                        event.categories.map { it.categoryId }.contains(category.categoryId)
                    )

            }
        }
    }

    @Test
    fun returnSameResultTypeAsInInput() = runBlocking {

        repository.returnLoadingState()
        val loadingResource = categoryUseCase(EventCategory.ALL).first()

        assert(loadingResource is Resource.Loading)

        repository.returnSuccessState()

        val successResource = categoryUseCase(EventCategory.ALL).first()

        assert(successResource is Resource.Success)

    }


}