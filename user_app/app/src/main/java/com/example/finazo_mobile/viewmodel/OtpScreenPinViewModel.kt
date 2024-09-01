package com.example.finazo_mobile.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finazo_mobile.entity.PreferencesManager
import com.example.finazo_mobile.model.PinSetter
import com.example.finazo_mobile.network.Retrofit.OtpRetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtpScreenPinViewModel: ViewModel() {

    private val _otp = MutableLiveData("")
    val otp: LiveData<String> = _otp

    private val _pin = MutableLiveData("")
    val pin: LiveData<String> = _pin

    private var otpFocusIndex = 0
    private var pinFocusIndex = 0

    private val _timerState = MutableLiveData(300) // 5 minutes in seconds
    val timerState: LiveData<Int> get() = _timerState

    private val _resendOtpVisible = MutableLiveData(false)
    val resendOtpVisible: LiveData<Boolean> get() = _resendOtpVisible

    init {
        startTimer()
    }

    fun onResendClicked(){
        if(_resendOtpVisible.value == true) {
            _timerState.value = 300
            _resendOtpVisible.value = false
            startTimer()
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_timerState.value!! > 0) {
                delay(1000L)
                _timerState.value = _timerState.value?.minus(1)
            }
            _resendOtpVisible.value = true
        }
    }

    fun onOtpChange(otp: String) {
        _otp.value = otp
    }

    fun onPinChange(pin: String) {
        _pin.value = pin
    }

     fun verifyOtpAndSetPin(pinsetter : PinSetter,context: Context) {
        if (pinsetter.otp.length == 6 && pinsetter.pin.length == 6 && pinsetter.email.isNotBlank()) {
            Log.d("verifyOtpAndSetPin", "verifyOtpAndSetPin")
            viewModelScope.launch(Dispatchers.IO) {
                val response = OtpRetrofitInstance.verifyOtpAndSetToke(pinsetter)

                if(response.isEmpty()){
                    Log.d("TOKEN EMPTY", "SOMETHING WENT WRONG")
                }else{
                    //set token
                    if(setJwtToken(response,context)){
                        //navigate
                    }
                }
            }
        }
    }

    private fun setJwtToken(token : String, context: Context):Boolean{
        val pref = PreferencesManager(context)

        try {
            pref.saveData("token", token)
        }catch (e : Exception){
            Log.e("PREF EXCEPTION" , e.message.toString())
            return false
        }
        return true
    }
}
