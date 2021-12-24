package com.geopark.feature_locations_events.presentation

sealed class UiEvent {
    data class ShowSnackbar(val message : String) : UiEvent()
}

