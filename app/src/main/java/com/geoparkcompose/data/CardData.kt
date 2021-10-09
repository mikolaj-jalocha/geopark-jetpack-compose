package com.geoparkcompose.data

import android.os.Parcelable

data class CardData(

    val id: String = "",
    val type: String = "",
    val name: String = "",
    val mapLocation: String = "",
    val description: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val website: String = "",

    //For Event type specific
    val eventDate: String = "",
    val price: String = ""


)