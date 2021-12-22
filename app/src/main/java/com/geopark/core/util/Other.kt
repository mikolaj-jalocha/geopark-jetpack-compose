package com.geopark.core.util

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.*


// TODO: Add description and potential utility
fun String.getDate() : String{

    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyyHH:mm")

    val toDate= LocalDateTime.parse(this,formatter)
    val fromNow = LocalDateTime.now()

    var temp = LocalDateTime.from(fromNow)

    val days = temp.until(toDate, ChronoUnit.DAYS)
    temp =  temp.plusDays(days)

    val hours = temp.until(toDate, ChronoUnit.HOURS)

    temp = temp.plusHours(hours)
    val minutes = temp.until(toDate, ChronoUnit.MINUTES)

    val day  = if(days > 0) "d" else ""
    val hour = if (hours > 0) "h" else ""
    val minute = "m"

    return "$days$day $hours$hour $minutes$minute"

}

fun DayOfWeek.getShortName(): String =
    this.getDisplayName(TextStyle.SHORT, Locale.getDefault())