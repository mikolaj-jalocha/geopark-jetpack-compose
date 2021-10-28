package com.geopark.feature_locations.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.use_case.LocationUseCases
import com.geopark.feature_locations.domain.util.LocationType
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val locationUseCases: LocationUseCases,
    private val locationsReference: CollectionReference
) : ViewModel() {

    private val _state = mutableStateOf(LocationsState())
    val state: State<LocationsState> = _state

    private var getLocationsJob: Job? = null

    init {

        locationsReference.addSnapshotListener { data, e ->
            if (e != null) {
                // TODO handle the error here
                return@addSnapshotListener
            }
            if (data != null) {
                viewModelScope.launch {
                    data.documents.forEach { doc ->
                        doc.toObject(Location::class.java)?.let {
                            locationUseCases.changeLocationData(
                                it
                            )
                        }
                    }
                }
            }
        }

        getLocations()
    }


    fun onEvent(event: LocationsEvent) {
        when (event) {
            is LocationsEvent.Type -> {
                _state.value = state.value.copy(
                    locationType = event.locationType
                )
            }
            is LocationsEvent.ChangeFavorite -> {
                viewModelScope.launch {
                    locationUseCases.changeLocationData(event.location.copy(isFavorite = event.newValue))
                }
            }
            is LocationsEvent.ChangeRecentlyWatched -> {
                viewModelScope.launch {
                    locationUseCases.changeLocationData(event.location.copy(wasRecentlyWatched = event.newValue))
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