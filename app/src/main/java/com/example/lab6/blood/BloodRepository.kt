package com.example.lab6.blood

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import java.io.IOException

class BloodRepository (val totalCount: Int, val pageSize: Int, private val context: Context){
    private val list: List<Blood>
    suspend fun getNation(page : Int): List<Blood> {

        val startIndex = (page - 1) * pageSize + 1
        var endIndex = startIndex + pageSize - 1
        if (endIndex > totalCount) {
            endIndex = totalCount
        }
        delay(2000)

        return (startIndex..endIndex).map { index -> list.get(index)}

        }
    init {
        list = getJson(context)

    }
    fun getJson(context: Context): List<Blood> {

        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("blood.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("ERROR JSON","$ioException")
        }

        val listCountryType = object : TypeToken<List<Blood>>() {}.type
        return Gson().fromJson(jsonString, listCountryType)
    }

}