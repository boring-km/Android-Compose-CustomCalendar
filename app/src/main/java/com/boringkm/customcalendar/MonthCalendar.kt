package com.boringkm.customcalendar

import android.util.Log
import java.util.Calendar
import java.util.Date


class MonthCalendar {

    private val calendar = Calendar.getInstance()
    var year: Int
    var month: Int
    var date: Int

    init {
        val now = Date()
        calendar.time = now

        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        date = calendar.get(Calendar.DATE)

        val maximum = calendar.getMaximum(Calendar.MONTH)
        Log.i("Calendar", "$maximum")
    }
}