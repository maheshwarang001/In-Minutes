package com.example.finazo_mobile.network.serviceInterface

import com.example.finazo_mobile.model.PinSetter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OtpService {

    @GET("email-verification")
    suspend fun getOtp(@Query("email") email : String):Boolean


    @POST("otp-verification")
    suspend fun verifyOtp(@Body pinSetter : PinSetter):String


}
