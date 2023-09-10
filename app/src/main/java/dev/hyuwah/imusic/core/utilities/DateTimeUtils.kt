package dev.hyuwah.imusic.core.utilities

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    private const val ITUNES_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val itunesDateTimeFormatter = DateTimeFormatter.ofPattern(ITUNES_DATE_TIME_FORMAT)

    fun String.getYear(): String {
        return try {
            val dateTime = LocalDateTime.parse(this, itunesDateTimeFormatter)
            dateTime.year.toString()
        } catch (e: Exception) {
            this
        }
    }

    fun Long.toTime(): String {
        val minutes = (this / 60000).toInt()
        val seconds = (this % 60000 / 1000).toInt()
        return buildString {
            append(String.format("%02d", minutes))
            append(":")
            append(String.format("%02d", seconds))
        }
    }
}