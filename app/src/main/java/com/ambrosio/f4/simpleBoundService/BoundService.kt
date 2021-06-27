package com.ambrosio.f4.simpleBoundService

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Binder
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

class BoundService : Service() {

    private val myBinder = MyLocalBinder()

    override fun onBind(intent: Intent) : IBinder {
        return myBinder
    }

    inner class MyLocalBinder : Binder() {
        fun getService() : BoundService {
            return this@BoundService
        }
    }

    fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US)
        return dateFormat.format(Date())
    }
}