package com.felipe.labs.countryapi.utils

import java.util.*

object DateOper {
    fun diffMinutes(dateStart: Date, dateEnd: Date): Long {
        val current = dateStart
        val last = dateEnd
        val diff = current.time - last.time
        return (diff / 1000) / 60
    }
    fun isInvalidMinutes(dateStart: Date, dateEnd: Date): Boolean {
       return diffMinutes(dateStart, dateEnd) < 30
    }
}