package com.example.finazo_mobile.entity

interface SharedPreferenceInterface {

    fun saveData(key:String , value:String)
    fun getData(key:String, defaultValue:String):String
}