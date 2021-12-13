package com.geopark.feature_locations.presentation.menu

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.core.util.Resource
import com.geopark.feature_locations.domain.use_case.LocationUseCases
import com.geopark.feature_locations.domain.util.LocationType
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
class MenuViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(LocationsState())
    val state: State<LocationsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var getLocationsJob: Job? = null


    init {
        getLocations(locationType = LocationType.All)
    }

    fun onEvent(menuEvent: MenuLocationsEvent) {
        when (menuEvent) {
            is MenuLocationsEvent.Type -> {
                if (menuEvent.locationType == state.value.locationType)
                    return

                getLocations(menuEvent.locationType)

            }
        }
    }


    private fun getLocations(locationType: LocationType) {


        getLocationsJob?.cancel()
        getLocationsJob = locationUseCases.getLocations(locationType)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {

                        _state.value = state.value.copy(
                            locations = result.data ?: emptyList(),
                            locationType = locationType,
                            isLoading = false
                        )
                    }
                    is Resource.Loading<*> -> {

                        _state.value = state.value.copy(
                            locations = result.data ?: emptyList(),
                            locationType = locationType,
                            isLoading = true
                        )
                    }
                    is Resource.Error<*> -> {
                        _state.value = state.value.copy(
                            locations = result.data ?: emptyList(),
                            locationType = locationType,
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

