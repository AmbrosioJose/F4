package com.ambrosio.f4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.ambrosio.f4.basicService.BasicServiceActivity
import com.ambrosio.f4.boundService.BoundServiceActivity
import com.ambrosio.f4.simpleBoundService.SimpleBoundServiceActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnBasicService: Button = findViewById(R.id.btnBasicService)
        val btnSimpleBoundService: Button = findViewById(R.id.btnSimpleBoundService)
        val btnBoundService: Button = findViewById(R.id.btnBoundService)

        btnBasicService.setOnClickListener{ launchBasicServiceActivity() }
        btnBoundService.setOnClickListener { launchBoundServiceActivity() }
        btnSimpleBoundService.setOnClickListener { launchSimpleBoundServiceActivity() }
    }

    private fun launchBasicServiceActivity(){
        val intent = Intent(this, BasicServiceActivity::class.java)
        startActivity(intent)
    }

    private fun launchBoundServiceActivity(){
        val intent = Intent(this, BoundServiceActivity::class.java)
        startActivity(intent)
    }

    private fun launchSimpleBoundServiceActivity(){
        val intent = Intent(this, SimpleBoundServiceActivity::class.java)
        startActivity(intent)
    }
}