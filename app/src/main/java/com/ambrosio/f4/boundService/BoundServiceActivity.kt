package com.ambrosio.f4.boundService

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ambrosio.f4.R
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Button
import android.view.View
import androidx.lifecycle.ViewModelProvider
import android.content.Intent
import android.content.Context
import android.os.Handler

class BoundServiceActivity : AppCompatActivity() {

    private lateinit var mProgressBar: ProgressBar
    private lateinit var mTextView: TextView
    private lateinit var mButton: Button

    private var mService: MyBoundService? = null
    private lateinit var mViewModel: BoundServiceActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bound_service)
        mProgressBar = findViewById(R.id.progressBar)
        mTextView = findViewById(R.id.tvProgress)
        mButton = findViewById(R.id.btnServiceControl)

        mViewModel = ViewModelProvider(this).get(BoundServiceActivityViewModel::class.java)

        mViewModel.getBinder().observe(this){ binder ->
            mService = if(binder != null){
                println("onChanged: connected to service")
                binder.getService()
            } else {
                println("onChanged: unbound from service")
                null
            }
        }

        mViewModel.getIsProgressUpdating().observe(this) { isUpdating ->

            // TODO: consider changing this to a timer
            val handler = Handler()
            val runnable = object : Runnable {
                override fun run() {
                    if(isUpdating){
                        if(mViewModel.getBinder().value != null){
                            if(mService!!.isFinished()){
                                mViewModel.setIsUpdating(false)

                            }
                            mProgressBar.progress = (mService!!.getProgress())
                            mProgressBar.max = (mService!!.getMaxValue())
                            val progress: String = (100 * mService!!.getProgress() / mService!!.getMaxValue()).toString()
                            mTextView.text = getString(R.string.progress_percent, progress)
                            handler.postDelayed(this, 100)
                        }
                    } else  {
                        handler.removeCallbacksAndMessages(null)
                    }
                }
            }

            if(isUpdating){
                mButton.text = getString(R.string.pause)
                handler.postDelayed(runnable, 100)
            } else {
                if(mService!!.isFinished()){
                    mButton.text = getString(R.string.restart)
                } else {
                    mButton.text = getString(R.string.start)
                }
            }


        }

        mButton.setOnClickListener { toggleUpdates() }
    }

    private fun toggleUpdates() {
        if(mService != null){
            if(mService!!.isFinished()){
                mService!!.resetTask()
                mButton.text = getString(R.string.start)
            } else {
                if(mService!!.isPaused()){
                    mService!!.unPausePretendLongRunningTask()
                    mViewModel.setIsUpdating(true)
                } else {
                    mService!!.pausePretendLongRunningTask()
                    mViewModel.setIsUpdating(false)
                }
            }
        }
    }


    override fun onStop(){
        super.onStop()
        if(mViewModel.getBinder() != null){
            unbindService(mViewModel.getServiceConnection())
        }
    }

    override fun onResume(){
        super.onResume()
        startService()
    }

    private fun startService(){
        println("Starting service")
        val serviceIntent = Intent(this, MyBoundService::class.java)
        startService(serviceIntent)
        bindService()
    }

    private fun bindService(){
        println("Binding Service")
        val serviceIntent = Intent(this, MyBoundService::class.java)
        bindService(serviceIntent, mViewModel.getServiceConnection(), Context.BIND_AUTO_CREATE)
    }
}