package com.example.finazo_mobile.entity

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) : SharedPreferenceInterface
{

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("finzo_pref", Context.MODE_PRIVATE)


    override fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getData(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}