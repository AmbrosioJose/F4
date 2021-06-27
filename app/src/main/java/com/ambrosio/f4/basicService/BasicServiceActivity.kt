package com.ambrosio.f4.basicService

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ambrosio.f4.R
import android.widget.Button
import android.content.Intent

class BasicServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_service)
        val btn: Button = findViewById(R.id.btnStartService)

        btn.setOnClickListener{
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }
    }


}