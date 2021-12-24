package com.geopark.feature_locations_events.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
    object CertificatedFirst : OrderType()
    object Default : OrderType()
}
