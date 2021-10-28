package com.geopark.feature_locations.domain.util

sealed class LocationOrder(val orderType: OrderType) {
    class Name(orderType: OrderType) : LocationOrder(orderType)
}
