package com.example.ui

import android.icu.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale.GERMANY

fun String.dateFormat(): String {
    val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    val date = LocalDate.parse(this, inputFormatter)
    val formattedDate = date.format(outputFormatter)
    return formattedDate
}

fun String.formatNumber(): String {
    return try {
        val number = this.toLong()
        val numberFormat = NumberFormat.getNumberInstance(GERMANY)
        numberFormat.format(number)
    } catch (e: NumberFormatException) {
        this
    }
}
