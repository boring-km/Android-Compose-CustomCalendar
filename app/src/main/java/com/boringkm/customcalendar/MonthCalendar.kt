package com.boringkm.customcalendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boringkm.customcalendar.model.CalendarDay
import java.util.Calendar
import java.util.Date


class MonthCalendar {

    private var lastDayOfMonth: Int

    private var today: CalendarDay
    private val calendar = Calendar.getInstance()
    private val monthCalendar = Calendar.getInstance()
    init {
        val todayCalendar = Calendar.getInstance()
        val now = Date()
        todayCalendar.time = now
        monthCalendar.time = now

        today = CalendarDay(
            todayCalendar.get(Calendar.YEAR),
            todayCalendar.get(Calendar.MONTH) + 1,
            todayCalendar.get(Calendar.DATE),
            true
        )

        lastDayOfMonth = todayCalendar.getMaximum(Calendar.DAY_OF_MONTH)
        Log.i("TAG", "현재 월: ${calendar.get(Calendar.MONTH) + 1}")
    }

    fun getDate(): String {
        return "${monthCalendar.get(Calendar.YEAR)}년 ${monthCalendar.get(Calendar.MONTH) + 1}월"
    }


    fun moveToNextMonth(): String {
        calendar.add(Calendar.MONTH, 0)
        monthCalendar.add(Calendar.MONTH, 1)
        _dayList.value = getWeekDays()
        return "${monthCalendar.get(Calendar.YEAR)}년 ${monthCalendar.get(Calendar.MONTH) + 1}월"
    }

    fun moveToBeforeMonth(): String {
        calendar.add(Calendar.MONTH, -2)
        monthCalendar.add(Calendar.MONTH, -1)
        _dayList.value = getWeekDays()
        return "${monthCalendar.get(Calendar.YEAR)}년 ${monthCalendar.get(Calendar.MONTH) + 1}월"
    }

    private val _dayList: MutableLiveData<MutableList<CalendarDay>> = MutableLiveData(getWeekDays())

    val dayList: LiveData<MutableList<CalendarDay>>
        get() = _dayList

    private fun getWeekDays(): MutableList<CalendarDay> {
        val tempDayList = mutableListOf<CalendarDay>()

        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val month = calendar.get(Calendar.MONTH) + 1
        for (i in 0..5) {
            for (j in 0..6) {
                calendar.add(Calendar.DAY_OF_MONTH, (1 - calendar.get(Calendar.DAY_OF_WEEK)) + j)

                tempDayList.add(
                    CalendarDay(
                        year = calendar.get(Calendar.YEAR),
                        month = calendar.get(Calendar.MONTH) + 1,
                        day = calendar.get(Calendar.DAY_OF_MONTH),
                        today = today.isToday(calendar)
                    )
                )
            }

            val lastDay = tempDayList.last()
            Log.i("check", "${lastDay.month}월 ${lastDay.day}일")
            if (lastDay.month != month) {
                break
            }
            calendar.add(Calendar.WEEK_OF_MONTH, 1)
        }
        return tempDayList
    }
}