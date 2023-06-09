package com.example.crfpos.model.request

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RequestConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromRequestList(requestList: List<Request>): String {
        return gson.toJson(requestList)
    }

    @TypeConverter
    fun toRequestList(requestListString: String): List<Request> {
        val listType = object : TypeToken<List<Request>>() {}.type
        return gson.fromJson(requestListString, listType)
    }
}
