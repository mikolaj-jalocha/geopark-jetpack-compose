package com.geopark.feature_locations_events.presentation.menu

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.use_case.locations.LocationUseCases
import com.geopark.feature_locations_events.domain.util.LocationType
import com.geopark.feature_locations_events.presentation.LocationsState
import com.geopark.feature_locations_events.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(LocationsState())
    val state: State<LocationsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getLocationsJobFlow: Job? = null

    init {
        getLocations(LocationType.All)
    }

    fun onEvent(menuEvent: MenuLocationsEvent) {
        when (menuEvent) {
            is MenuLocationsEvent.Type -> {
                if (menuEvent.locationType == state.value.locationType)
                    return
                else {
                    getLocations(menuEvent.locationType)
                }
            }
            else -> {

            }
        }
    }

    private fun getLocations(locationType: LocationType) {

        getLocationsJobFlow?.cancel()
        getLocationsJobFlow = viewModelScope.launch {
            locationUseCases.getLocationsByTypeUseCase(locationType).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            locations = result.data,
                            locationType = locationType,
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


    }




