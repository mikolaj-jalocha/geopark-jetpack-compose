package com.geopark.feature_locations.presentation.content

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.use_case.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContentViewModel @Inject constructor(
    private val locationsUseCases: LocationUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _locationsState = mutableStateOf(Location())
    val locationsState: State<Location> = _locationsState

    private val _eventFlow = MutableSharedFlow<ContentEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var eventJob: Job? = null

    fun onEvent(contentEvent: ContentEvent) {
        eventJob?.cancel()
        eventJob = viewModelScope.launch {
            when (contentEvent) {
                is ContentEvent.ShowOnMap -> {
                    _eventFlow.emit(
                        contentEvent
                    )
                }
                is ContentEvent.Call -> {
                    _eventFlow.emit(
                        contentEvent
                    )
                }
            }
        }
    }

    init {
        val locationName = savedStateHandle.get<String>("locationName") ?: ""
        if (locationName.isNotBlank()) {
                viewModelScope.launch {
                   _locationsState.value = locationsUseCases.getLocationByName(locationName)
                }
        }

    }

}