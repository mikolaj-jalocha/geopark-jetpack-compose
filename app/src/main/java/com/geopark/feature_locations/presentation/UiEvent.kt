package com.geopark.feature_locations.presentation

sealed class UiEvent {
    data class ShowSnackbar(val message : String) : UiEvent()
}

