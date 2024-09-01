package com.example.finazo_mobile.network.Retrofit

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finazo_mobile.model.PinSetter
import com.example.finazo_mobile.model.RegistrationOtp
import com.example.finazo_mobile.network.BASE_URL_OTP
import com.example.finazo_mobile.network.serviceInterface.OtpService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OtpRetrofitInstance {

    suspend fun getOtp(otp: RegistrationOtp): Boolean {
        return try {
            val response = _apiOtp.getOtp(otp.email)
            response // Assume the response is a Boolean
        } catch (e: Exception) {
            Log.e("OTP error", "getOtp: $e")
            false // Return false on error
        }
    }

    suspend fun verifyOtpAndSetToke(pin : PinSetter) : String{
        return try {
            val response = _apiOtp.verifyOtp(pin)
            response
        }catch (e: Exception){
            Log.e("Otp Verification Exception" , "$e")
            ""
        }
    }

    private val _apiOtp: OtpService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_OTP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OtpService::class.java)
    }


}