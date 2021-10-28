package com.geopark.feature_locations.domain.util

sealed class LocationType{
    object All : LocationType()
    object Hotel : LocationType()
    object Restaurant : LocationType()
    object Active : LocationType()
    object Place : LocationType()
}
