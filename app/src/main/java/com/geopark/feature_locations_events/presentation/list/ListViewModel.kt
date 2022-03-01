package com.geopark.feature_locations_events.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.use_case.locations.LocationUseCases
import com.geopark.feature_locations_events.domain.util.LocationType
import com.geopark.feature_locations_events.domain.util.SortType
import com.geopark.feature_locations_events.presentation.LocationsState
import com.geopark.feature_locations_events.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(LocationsState())
    val state: State<LocationsState> = _state

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var getLocationsJob: Job? = null
    private var getLocationsJobFlow: Job? = null


    init {
        val locationType = when (savedStateHandle.get<String>("locationType") ?: "All") {
            LocationType.Hotel.toString() -> LocationType.Hotel
            LocationType.Restaurant.toString() -> LocationType.Restaurant
            LocationType.Active.toString() -> LocationType.Active
            LocationType.Explore.toString() -> LocationType.Explore
            else -> LocationType.All
        }

        getLocationsFlow(locationType, SortType.Default)
    }


    private fun getLocationsFlow(
        locationType: LocationType,
        sortType: SortType,
        searchQuery: String = ""
    ) {
        getLocationsJobFlow?.cancel()
        getLocationsJobFlow = viewModelScope.launch {
            locationUseCases.getFilteredLocationsUseCase(
                searchQuery,
                locationType,
                sortType
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            locations = result.data,
                            locationType = locationType,
                            sortType = sortType,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            locations = result.data,
                            locationType = locationType,
                            isLoading = true
                        )
                    }
                    else -> {
                    }
                }

            }
        }
    }


    fun onEvent(listEvent: ListLocationsEvent) {
        when (listEvent) {
            is ListLocationsEvent.ChangeSearchQuery -> {
                _searchQuery.value = listEvent.query
                getLocationsFlow(
                    state.value.locationType,
                    state.value.sortType,
                    listEvent.query
                )
            }

            is ListLocationsEvent.Order -> {
                if (state.value.sortType::class == listEvent.sortType::class &&
                    state.value.sortType == listEvent.sortType
                ) {
                    return
                }
                getLocationsFlow(state.value.locationType, listEvent.sortType)
            }
        }
    }

}
