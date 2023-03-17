package com.boringkm.customcalendar

import android.util.Log
import com.boringkm.customcalendar.model.CalendarDay
import java.util.Calendar
import java.util.Date


class MonthCalendar {

    var lastDayOfMonth: Int

    var today: CalendarDay

    init {
        val calendar = Calendar.getInstance()
        val now = Date()
        calendar.time = now

        today = CalendarDay(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DATE),
            true
        )

        lastDayOfMonth = calendar.getMaximum(Calendar.DAY_OF_MONTH)
    }

    fun getWeekDays(): MutableList<CalendarDay> {
        val dayList = mutableListOf<CalendarDay>()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val month = calendar.get(Calendar.MONTH) + 1
        for (i in 0..5) {
            for (j in 0..6) {
                calendar.add(Calendar.DAY_OF_MONTH, (1 - calendar.get(Calendar.DAY_OF_WEEK)) + j)

                dayList.add(
                    CalendarDay(
                        year = calendar.get(Calendar.YEAR),
                        month = calendar.get(Calendar.MONTH) + 1,
                        day = calendar.get(Calendar.DAY_OF_MONTH),
                        today = today.isToday(calendar)
                    )
                )

            }

            val lastDay = dayList.last()
            Log.i("check", "${lastDay.month}월 ${lastDay.day}일")
            if (lastDay.month != month) {
                break
            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }
        return dayList
    }
}