package com.sanmer.mrepo.utils

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class StringListTypeConverter {
    private val moshi = Moshi.Builder().build()
    private val type = Types.newParameterizedType(List::class.java, String::class.java)
    private val adapter: JsonAdapter<List<String>> = moshi.adapter(type)

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String> {
        return value?.let { adapter.fromJson(it) } ?: emptyList()
    }
}