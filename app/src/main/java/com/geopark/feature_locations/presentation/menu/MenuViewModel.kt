package com.geopark.feature_locations.presentation.menu

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.feature_locations.domain.use_case.LocationUseCases
import com.geopark.feature_locations.domain.util.LocationType
import com.geopark.feature_locations.presentation.LocationsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(LocationsState())
    val state: State<LocationsState> = _state

    private var getLocationsJob: Job? = null


    init {
        getLocations()
    }


    fun onEvent(menuEvent: MenuLocationsEvent) {
        when (menuEvent) {
            is MenuLocationsEvent.Type -> {
                if(menuEvent.locationType == state.value.locationType)
                    return
                getLocations(menuEvent.locationType)
            }
            is MenuLocationsEvent.ChangeFavorite -> {
                viewModelScope.launch {
                    locationUseCases.changeLocationData(menuEvent.location.copy(isFavorite = menuEvent.newValue))
                }
            }
            is MenuLocationsEvent.ChangeRecentlyWatched -> {
                viewModelScope.launch {
                    locationUseCases.changeLocationData(menuEvent.location.copy(wasRecentlyWatched = menuEvent.newValue))
                }

            }
        }
    }

    private fun getLocations(locationType: LocationType = LocationType.All) {
        getLocationsJob?.cancel()
        locationUseCases.getLocations(locationType)
            .onEach { locations ->
                _state.value = state.value.copy(
                    locations = locations,
                    locationType = locationType
                )
            }.launchIn(viewModelScope)
    }


}