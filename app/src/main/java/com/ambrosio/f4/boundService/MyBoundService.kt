package com.ambrosio.f4

import android.os.IBinder
import android.os.Binder
import android.os.Handler
import android.app.Service
import android.content.Intent
import com.ambrosio.f4.basicService.MyService
import java.lang.Runnable

class MyBoundService : Service() {

    private val mBinder: IBinder = MyBinder()
    private var mProgress = 0
    private var mMaxValue = 100
    private var mIsPaused = false
    private lateinit var mHandler: Handler

    override fun onCreate(){
        super.onCreate()
        mHandler = Handler()
        mProgress = 0
        mIsPaused = true
        mMaxValue = 5000
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }



//    override fun onHandleIntent(intent: Intent?) {
//        println("Intent service started")
//    }

    class MyBinder: Binder(){

        fun getService(): MyService {
            return MyService()
        }
    }

    fun startPretendLongRunningTask(){
        val runnable: Runnable = Runnable {
            if(mProgress >= mMaxValue || mIsPaused){
                println("run: removing callbacks")
//                mHandler.removeCallbacks()
                pausePretendLongRunningTask()
            } else {
                println("run: Progress $mProgress")
            }

        }
        mHandler.postDelayed(runnable, 1000)
    }

    private fun pausePretendLongRunningTask(){
        mIsPaused = true
    }

    private fun unPausePretendLongRunningTask(){
        mIsPaused = false
        startPretendLongRunningTask()
    }

    private fun isPaused(): Boolean {
        return mIsPaused
    }

    private fun getProgress(): Int {
        return mProgress
    }

    private fun getMaxValue(): Int {
        return mMaxValue
    }

    private fun resetTask(){
        mProgress = 0
    }

    override fun onTaskRemoved(rootIntent: Intent){
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        println("Service onDestroy")
    }
}