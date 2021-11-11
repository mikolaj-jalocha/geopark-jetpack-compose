package com.geopark.feature_locations.domain.util

sealed class LocationOrder(val orderType: OrderType) {
    class Name(orderType: OrderType) : LocationOrder(orderType)
    // TODO: 11.11.2021 Add other LocationsOrder
}
