package com.geopark.core.util


typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T, val message: String = "") {
    class Loading<T>(data: T): Resource<T>(data)
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T): Resource<T>(data, message)
}