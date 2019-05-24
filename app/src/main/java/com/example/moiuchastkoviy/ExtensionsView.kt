package com.example.moiuchastkoviy

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun EditText.validate(
    validator: (String) -> Boolean,
    message: String?
): Boolean {
    val flag = validator(this.text.toString())
    this.error = if (flag) null else message

    return flag
}


fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG): Toast =
    Toast.makeText(this, message, duration).apply {
        show()
    }

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_LONG): Toast =
    Toast.makeText(this, resId, duration).apply {
        show()
    }

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.hide(hide: Boolean) {
    if (hide) gone() else visible()
}

fun View.show(show: Boolean = true) {
    if (show) visible() else gone()
}


fun getDate(date: String): String {
    var s = getFormattedDate(date)
    Log.e("Time", s)
    s = s.replace("Yesterday", "Вчера")
    s = s.replace("Today", "Сегодня")

    return s
}

fun getFormattedDate(s: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
    var date: Date? = null
    try {
        date = format.parse(s)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    val smsTimeInMilis = date!!.getTime()

    val smsTime = Calendar.getInstance()
    smsTime.timeInMillis = smsTimeInMilis

    val now = Calendar.getInstance()

    val timeFormatString = "h:mm aa"
    val dateTimeFormatString = "EEEE, MMMM d, h:mm aa"
    val HOURS = (60 * 60 * 60).toLong()
    return if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
        "Today " + DateFormat.format(timeFormatString, smsTime)
    } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
        "Yesterday " + DateFormat.format(timeFormatString, smsTime)
    } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
        DateFormat.format(dateTimeFormatString, smsTime).toString()
    } else {
        DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString()
    }

}

