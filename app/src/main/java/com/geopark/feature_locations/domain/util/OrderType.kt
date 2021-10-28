package com.geopark.feature_locations.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
