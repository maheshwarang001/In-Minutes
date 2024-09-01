package com.example.rider_app.viewmodel

import android.Manifest
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class HomeViewModel : ViewModel() {

    val visiblePermissionQueue = mutableStateListOf<String>()

    private val _isChecked = mutableStateOf(false)
    val isChecked: State<Boolean> = _isChecked


    private val _timeLeft = MutableLiveData<String>()
    val timeLeft: LiveData<String> = _timeLeft

    private var startTime: Long = 0
    private val handler = Handler(Looper.getMainLooper())


    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            val timeElapsed = currentTime - startTime

            val hours = TimeUnit.MILLISECONDS.toHours(timeElapsed)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % 60

            Log.d("CountdownTimer", "Time elapsed: $hours hours, $minutes minutes")

            _timeLeft.value = String.format("%02d:%02d", hours, minutes)
            handler.postDelayed(this, 1000)
        }
    }

    private fun startCountdown(startTimeInMillis: Long) {
        startTime = startTimeInMillis
        handler.post(updateTimeRunnable)
    }

    private fun stopAndResetCountdown() {
        handler.removeCallbacks(updateTimeRunnable)
        _timeLeft.value = "00:00"
    }



    fun onCheckedChange(checked: Boolean) {
        _isChecked.value = checked

        if (checked) {
            startCountdown(System.currentTimeMillis())
        } else {
            stopAndResetCountdown()
        }
    }


    fun dismissDialog(){
        visiblePermissionQueue.removeLast()
    }


    fun onPermissionResult(
        permission : String,
        isGranted : Boolean
    ){
        if(!isGranted && !visiblePermissionQueue.contains(permission)) {
            visiblePermissionQueue.add(permission)
        }
    }


    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS,
        Manifest.permission.FOREGROUND_SERVICE,
        Manifest.permission.FOREGROUND_SERVICE_LOCATION,
    )


}