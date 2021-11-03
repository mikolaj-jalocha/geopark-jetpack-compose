package com.geopark.feature_locations.domain.util


sealed class LocationType{
    object All : LocationType()
    object Hotel : LocationType()
    object Restaurant : LocationType()
    object Active : LocationType()
    object Place : LocationType()

    override fun toString(): String {
        return when(this){
            is All -> "All"
            is Hotel -> "Hotel"
            is Restaurant -> "Restaurant"
            is Active -> "Active"
            is Place -> "Place"
        }
    }

}
