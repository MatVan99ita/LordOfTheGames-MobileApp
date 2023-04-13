package com.example.lordofthegames.Database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.*


class DateConverter {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let { Date(it) }
    }
}