package alzaichsank.com.aplikasifootbalmatchschedule.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTime {

    private fun formatDate(date: String, format: String): String {
        var result = ""
        val old = SimpleDateFormat("yyyy-MM-dd")
        try {
            val oldDate = old.parse(date)
            val newFormat = SimpleDateFormat(format)
            result = newFormat.format(oldDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return result
    }

    fun getLongDate(date: String?): String {
        return formatDate(date.toString(), "EEE, dd MMM yyyy")
    }

    fun dateTimeToFormat(date: String?, time: String?): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$date $time"
        val date = formatter.parse(dateTime)
        return date.time
    }

    fun toGMTFormat(date: String?, time: String?): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val dateTime = "$date $time"
        return formatter.parse(dateTime)
    }

    fun getBasicTime(date: Date): String? {
        val sd = SimpleDateFormat("HH:mm")
        return sd.format(date)
    }

}
