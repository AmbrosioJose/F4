package com.ambrosio.f4.basicService

import android.content.Intent
import android.app.IntentService

class MyService : IntentService("MyService") {

    override fun onHandleIntent(intent: Intent?) {
        println("Intent service started")
    }

}