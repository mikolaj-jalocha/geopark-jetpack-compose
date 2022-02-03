package com.geopark.feature_locations_events.domain.util


sealed class LocationType{
    object All : LocationType()
    object Hotel : LocationType()
    object Restaurant : LocationType()
    object Active : LocationType()
    object Explore : LocationType()

    override fun toString(): String {
        return when(this){
            is All -> "All"
            is Hotel -> "noclegi"
            is Restaurant -> "gastronomia"
            is Active -> "sport"
            is Explore -> "Explore"
        }
    }

}
