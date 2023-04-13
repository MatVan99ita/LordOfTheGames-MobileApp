package com.example.lordofthegames.Database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class AnyTypeConverter {
    @TypeConverter
    fun fromAny(value: Any?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toAny(value: String?): Any? {
        return value
    }
}