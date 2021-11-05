package com.geopark.feature_locations.presentation.content

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.use_case.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContentViewModel @Inject constructor(
    private val locationsUseCases: LocationUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(Location())
    val state: State<Location> = _state


    init {
        val locationName = savedStateHandle.get<String>("locationName") ?: ""
        if (locationName.isNotBlank()) {
                viewModelScope.launch {
                   _state.value = locationsUseCases.getLocationByName(locationName)
                }
        }

    }

}