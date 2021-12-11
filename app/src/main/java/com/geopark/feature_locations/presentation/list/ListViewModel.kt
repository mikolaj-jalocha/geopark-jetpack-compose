package com.geopark.feature_locations.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.core.util.Resource
import com.geopark.feature_locations.domain.use_case.LocationUseCases
import com.geopark.feature_locations.domain.util.LocationOrder
import com.geopark.feature_locations.domain.util.LocationType
import com.geopark.feature_locations.domain.util.OrderType
import com.geopark.feature_locations.presentation.LocationsState
import com.geopark.feature_locations.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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


    init {
        val locationType = when (savedStateHandle.get<String>("locationType") ?: "All") {
            LocationType.Hotel.toString() -> LocationType.Hotel
            LocationType.Restaurant.toString() -> LocationType.Restaurant
            LocationType.Active.toString() -> LocationType.Active
            LocationType.Explore.toString() -> LocationType.Explore
            else -> LocationType.All
        }

        getLocations(locationType, LocationOrder.Name(OrderType.Default))
    }

    fun onEvent(listEvent: ListLocationsEvent) {
        when (listEvent) {
            is ListLocationsEvent.ChangeSearchQuery -> {
                _searchQuery.value = listEvent.query

                getLocations(
                    state.value.locationType,
                    state.value.locationOrder,
                    listEvent.query
                )
            }

            is ListLocationsEvent.ChangeFavorite -> {
                viewModelScope.launch {
                    locationUseCases.changeLocationData(listEvent.location.copy(isFavorite = listEvent.newValue))
                }
            }
            is ListLocationsEvent.ChangeRecentlyWatched -> {
                viewModelScope.launch {
                    locationUseCases.changeLocationData(listEvent.location.copy(wasRecentlyWatched = listEvent.newValue))
                }
            }
            is ListLocationsEvent.Order -> {
                if (state.value.locationOrder::class == listEvent.locationOrder::class &&
                    state.value.locationOrder.orderType == listEvent.locationOrder.orderType
                ) {
                    return
                }
                getLocations(state.value.locationType, listEvent.locationOrder)
            }
        }
    }

    private fun getLocations(
        locationType: LocationType,
        locationOrder: LocationOrder,
        searchQuery: String = ""
    ) {
        viewModelScope.launch {
            getLocationsJob?.cancel()
            getLocationsJob =
                locationUseCases.getLocations(locationType, locationOrder,searchQuery).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                locations = result.data ?: emptyList(),
                                locationType = locationType,
                                locationOrder = locationOrder,
                                isLoading = false
                            )
                        }
                        is Resource.Loading<*> -> {
                            _state.value = state.value.copy(
                                locations = result.data ?: emptyList(),
                                locationType = locationType,
                                locationOrder = locationOrder,
                                isLoading = true
                            )
                        }
                        is Resource.Error<*> -> {
                            _state.value = state.value.copy(
                                locations = result.data ?: emptyList(),
                                locationType = locationType,
                                locationOrder = locationOrder,
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = result.message ?: "Unknown error"
                                )
                            )
                        }
                    }

                }.launchIn(viewModelScope)
        }
    }
}