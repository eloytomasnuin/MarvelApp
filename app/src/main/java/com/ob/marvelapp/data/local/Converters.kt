package com.ob.marvelapp.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    private val moshi = Moshi.Builder().build()

    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = Types.newParameterizedType(
            List::class.java,
            String::class.java
        )
        return moshi.adapter<ArrayList<String>>(listType).fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {

        val listType = Types.newParameterizedType(
            List::class.java,
            String::class.java
        )
        return moshi.adapter<List<String>>(listType).toJson(list)
    }
}