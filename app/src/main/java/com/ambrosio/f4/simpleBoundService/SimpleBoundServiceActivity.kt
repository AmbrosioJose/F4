package com.ambrosio.f4.simpleBoundService

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ambrosio.f4.R
import android.view.View
import android.widget.TextView
import android.widget.Button
import android.content.Intent
import android.content.ServiceConnection
import android.content.ComponentName
import android.content.Context
import android.os.IBinder

class SimpleBoundServiceActivity : AppCompatActivity() {
    var myService: BoundService? = null
    var isBound = false

    lateinit var tvDate: TextView
    lateinit var btnStartService: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_bound_service)
        tvDate = findViewById(R.id.tvDate)
        btnStartService = findViewById(R.id.btnShowTime)


        val intent = Intent(this, BoundService::class.java )
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)

        btnStartService.setOnClickListener { showTime(it) }
    }

    private val myConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder){
            val binder = service as BoundService.MyLocalBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }

    }

    private fun showTime(view: View){
        val currentTime = myService?.getCurrentTime()
        tvDate.text = currentTime
    }


}