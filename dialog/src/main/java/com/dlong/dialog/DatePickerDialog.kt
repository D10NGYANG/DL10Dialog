package com.dlong.dialog

import android.content.Context
import com.dlong.dialog.impl.OnPickItemSelectListener
import java.lang.Exception
import java.util.*

/**
 * 日期选择器
 *
 * @author D10NG
 * @date on 2020/8/25 7:10 PM
 */
class DatePickerDialog constructor(
    context: Context,
    // 开始时间 1976-01-01 00:00:00
    val startTime: Long = 189273600000L,
    // 结束时间 最新时间
    val endTime: Long = System.currentTimeMillis(),
    // 选择时间
    val selectTime: Long = System.currentTimeMillis()
): PickerDialog(context) {

    init {
        if (startTime >= endTime) {
            throw Exception("DatePickerDialog startTime >= endTime")
        } else if (selectTime !in startTime .. endTime) {
            throw Exception("DatePickerDialog selectTime !in startTime .. endTime")
        }
    }

    companion object {
        private val tagY = "year"
        private val tagM = "month"
        private val tagD = "day"
    }

    /** 前后文本 */
    private var yearStartStr = ""
    private var yearEndStr = ""
    private var monthStartStr = ""
    private var monthEndStr = ""
    private var dayStartStr = ""
    private var dayEndStr = ""

    /** 日期选中监听器 */
    val datePickDialogListener: (OnPickItemSelectListener<DatePickerDialog>.() -> Unit) = {
        onSelect { dialog: DatePickerDialog, tag, position, selectItem ->
            val selectYear = getYearPickValue()
            val selectMonth = getMonthPickValue()
            val selectDay = getDayPickValue()
            when(tag) {
                tagY -> {
                    if (!dialog.isHasTag(tagM)) return@onSelect
                    dialog.removePickList(tagM)
                    when (selectYear) {
                        getTimeYear(startTime) -> {
                            val monthList = getMonthList(selectYear)
                            val newSelectMonth = selectMonth.coerceAtLeast(getTimeMonth(startTime))
                            dialog.addPickList(
                                tagM, "$newSelectMonth", monthList,
                                monthStartStr, monthEndStr, null, this
                            )
                            if (dialog.isHasTag(tagD)) {
                                dialog.removePickList(tagD)
                                val dayList = getDayList(selectYear, newSelectMonth)
                                val dayMin =
                                    if (newSelectMonth == getTimeMonth(startTime)) getTimeDay(startTime) else 1
                                val dayMax = getDaysOfMonth(selectYear, newSelectMonth)
                                val newDaySelect = selectDay.coerceAtLeast(dayMin).coerceAtMost(dayMax)
                                dialog.addPickList(
                                    tagD, "$newDaySelect", dayList,
                                    dayStartStr, dayEndStr
                                )
                            }
                        }
                        getTimeYear(endTime) -> {
                            val monthList = getMonthList(selectYear)
                            val newSelectMonth = selectMonth.coerceAtMost(getTimeMonth(endTime))
                            dialog.addPickList(
                                tagM, "$newSelectMonth", monthList,
                                monthStartStr, monthEndStr, null, this
                            )
                            if (dialog.isHasTag(tagD)) {
                                dialog.removePickList(tagD)
                                val dayList = getDayList(selectYear, newSelectMonth)
                                val dayMin = 1
                                val dayMax =
                                    if (newSelectMonth == getTimeMonth(endTime)) getTimeDay(endTime) else getDaysOfMonth(
                                        selectYear,
                                        newSelectMonth
                                    )
                                val newDaySelect = selectDay.coerceAtLeast(dayMin).coerceAtMost(dayMax)
                                dialog.addPickList(
                                    tagD, "$newDaySelect", dayList,
                                    dayStartStr, dayEndStr
                                )
                            }
                        }
                        else -> {
                            val monthList = getMonthList(selectYear)
                            dialog.addPickList(
                                tagM, "$selectMonth", monthList,
                                monthStartStr, monthEndStr, null, this
                            )
                            if (dialog.isHasTag(tagD)) {
                                dialog.removePickList(tagD)
                                val dayList = getDayList(selectYear, selectMonth)
                                val dayMin = 1
                                val dayMax = getDaysOfMonth(selectYear, selectMonth)
                                val newDaySelect = selectDay.coerceAtLeast(dayMin).coerceAtMost(dayMax)
                                dialog.addPickList(
                                    tagD, "$newDaySelect", dayList,
                                    dayStartStr, dayEndStr
                                )
                            }
                        }
                    }
                }
                tagM -> {
                    if (!dialog.isHasTag(tagD)) return@onSelect
                    dialog.removePickList(tagD)
                    val dayList = getDayList(selectYear, selectMonth)
                    val dayMin = if (selectYear == getTimeYear(startTime) && selectMonth == getTimeMonth(startTime)) getTimeDay(startTime) else 1
                    val dayMax = if (selectYear == getTimeYear(endTime) && selectMonth == getTimeMonth(endTime)) getTimeDay(endTime) else getDaysOfMonth(selectYear, selectMonth)
                    val newDaySelect = selectDay.coerceAtLeast(dayMin).coerceAtMost(dayMax)
                    dialog.addPickList(tagD, "$newDaySelect", dayList,
                        dayStartStr, dayEndStr)
                }
            }
        }
    }

    /**
     * 创建默认的日期选择器
     * @return [DatePickerDialog]
     */
    fun normalBuilder(): DatePickerDialog {
        setYearPickList()
        setMonthPickList()
        setDayPickList()
        return this
    }

    /**
     * 设置年份选择
     * @param start
     * @param end
     * @return [DatePickerDialog]
     */
    fun setYearPickList(start: String = "", end: String = ""): DatePickerDialog {
        yearStartStr = start
        yearEndStr = end
        val yearList = getYearList()
        if (isHasTag(tagY)) removePickList(tagY)
        this.addPickList(tagY, "${getTimeYear(selectTime)}", yearList,
            start, end, datePickDialogListener)
        return this
    }

    /**
     * 设置月份选择
     * @param start
     * @param end
     * @return [DatePickerDialog]
     */
    fun setMonthPickList(start: String = "", end: String = ""): DatePickerDialog {
        monthStartStr = start
        monthEndStr = end
        val monthList = getMonthList(getTimeYear(selectTime))
        this.addPickList(tagM, "${getTimeMonth(selectTime)}", monthList,
            monthStartStr, monthEndStr, datePickDialogListener)
        return this
    }

    /**
     * 设置日期选择
     * @param start
     * @param end
     * @return [DatePickerDialog]
     */
    fun setDayPickList(start: String = "", end: String = ""): DatePickerDialog {
        dayStartStr = start
        dayEndStr = end
        val dayList = getDayList(getTimeYear(selectTime), getTimeMonth(selectTime))
        this.addPickList(tagD, "${getTimeDay(selectTime)}", dayList,
            dayStartStr, dayEndStr)
        return this
    }

    /**
     * 获取选中年份
     * @return [Int]
     */
    fun getYearPickValue(): Int = getPickOnTag(tagY).toIntOrNull()?: getTimeYear(selectTime)

    /**
     * 获取选中月份
     * @return [Int]
     */
    fun getMonthPickValue(): Int = getPickOnTag(tagM).toIntOrNull()?: getTimeMonth(selectTime)

    /**
     * 获取选中日期
     * @return [Int]
     */
    fun getDayPickValue(): Int = getPickOnTag(tagD).toIntOrNull()?: getTimeDay(selectTime)

    /**
     * 获取年列表
     * @return [List]
     */
    fun getYearList(): List<String> {
        val list = mutableListOf<String>()
        for (i in getTimeYear(startTime) .. getTimeYear(endTime)) {
            list.add("$i")
        }
        return list
    }

    /**
     * 获取月列表
     * @param selectYear 选中年份
     * @return [List]
     */
    fun getMonthList(selectYear: Int): List<String> {
        val list = mutableListOf<String>()
        when (selectYear) {
            getTimeYear(startTime) -> {
                for (i in getTimeMonth(startTime) .. 12) {
                    list.add("$i")
                }
            }
            getTimeYear(endTime) -> {
                for (i in 1 .. getTimeMonth(endTime)) {
                    list.add("$i")
                }
            }
            else -> {
                for (i in 1 .. 12) {
                    list.add("$i")
                }
            }
        }
        return list
    }

    /**
     * 获取日列表
     * @param selectYear 选中年份
     * @param selectMonth 选中月份
     * @return [List]
     */
    fun getDayList(selectYear: Int, selectMonth: Int): List<String> {
        val list = mutableListOf<String>()
        when(selectYear) {
            getTimeYear(startTime) -> {
                val dayMin = if (selectMonth == getTimeMonth(startTime))
                    getTimeDay(endTime) else 1
                for (i in dayMin .. getDaysOfMonth(selectYear, selectMonth)) {
                    list.add("$i")
                }
            }
            getTimeYear(endTime) -> {
                val dayMax = if (selectMonth == getTimeMonth(endTime))
                    getTimeDay(endTime) else getDaysOfMonth(selectYear, selectMonth)
                for (i in 1 .. dayMax) {
                    list.add("$i")
                }
            }
            else -> {
                for (i in 1 .. getDaysOfMonth(selectYear, selectMonth)) {
                    list.add("$i")
                }
            }
        }
        return list
    }

    /**
     * 获取指定月份的天数
     * @param year
     * @param month
     */
    private fun getDaysOfMonth(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month -1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    /**
     * 获取时间戳中的年份
     * @param time
     */
    private fun getTimeYear(time: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar.get(Calendar.YEAR)
    }

    /**
     * 获取时间戳中的月份
     * @param time
     */
    private fun getTimeMonth(time: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar.get(Calendar.MONTH) + 1
    }

    /**
     * 获取时间戳中的日期
     * @param time
     */
    private fun getTimeDay(time: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar.get(Calendar.DAY_OF_MONTH)
    }
}