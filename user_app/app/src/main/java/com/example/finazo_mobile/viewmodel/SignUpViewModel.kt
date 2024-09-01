package com.example.finazo_mobile.viewmodel

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finazo_mobile.model.RegistrationOtp
import com.example.finazo_mobile.network.Retrofit.OtpRetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {


    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    private val _request = MutableLiveData(false)
    val request: LiveData<Boolean> = _request

    fun isCheck():Boolean{
        return !_email.value.isNullOrBlank()
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onSignUpClick() {
        val emailValue = _email.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val response = OtpRetrofitInstance.getOtp(RegistrationOtp(emailValue))
            _request.postValue(response)
        }
    }

    fun clearData() {
        _email.value = ""
        _request.value = false
    }
}