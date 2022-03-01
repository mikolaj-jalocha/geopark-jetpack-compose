package com.geopark.feature_locations_events.domain.util

sealed class SortType {
    object Ascending : SortType()
    object Descending : SortType()
    object Default  : SortType()
}

