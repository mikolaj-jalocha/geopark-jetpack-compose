package com.geopark.feature_locations_events.data.remote

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


import android.net.ConnectivityManager




class ConnectivityInterceptor(val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline(context)) {
            throw NoInternetConnection()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}

class NoInternetConnection : IOException() {
    override val message: String
        get() = "No Internet connection"
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val netInfo = connectivityManager.activeNetwork
    return netInfo != null
}