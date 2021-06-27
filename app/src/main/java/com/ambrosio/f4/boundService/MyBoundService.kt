package com.ambrosio.f4.boundService

import android.os.IBinder
import android.os.Binder
import android.os.Handler
import android.app.Service
import android.content.Intent
import java.lang.Runnable

class MyBoundService : Service() {

    private val mBinder: IBinder = MyBinder()
    private var mProgress = 0
    private var mMaxValue = 100
    private var mIsPaused = false
    private lateinit var mHandler: Handler

    override fun onCreate(){
        super.onCreate()
        println("MyBoundService onCreate")
        mHandler = Handler()
        mProgress = 0
        mIsPaused = true
        mMaxValue = 5000
    }

    override fun onBind(intent: Intent?): IBinder {
        println("onBind")
        return mBinder
    }



//    override fun onHandleIntent(intent: Intent?) {
//        println("Intent service started")
//    }

    inner class MyBinder: Binder(){

        fun getService(): MyBoundService {
            return this@MyBoundService
        }
    }

    private fun startPretendLongRunningTask(){
        val runnable = object :  Runnable {
            override fun run(){
                if(mProgress >= mMaxValue || mIsPaused){
                    println("run: removing callbacks")
                    mHandler.removeCallbacksAndMessages(null)
                    pausePretendLongRunningTask()
                } else {
                    println("run: Progress $mProgress")
                    mProgress += 100
                    mHandler.postDelayed(this, 100)
                }
            }
        }
        mHandler.postDelayed(runnable, 100)
    }

    fun pausePretendLongRunningTask(){
        mIsPaused = true
    }

    fun unPausePretendLongRunningTask(){
        mIsPaused = false
        startPretendLongRunningTask()
    }

    fun isPaused(): Boolean {
        return mIsPaused
    }

    fun getProgress(): Int {
        return mProgress
    }

    fun getMaxValue(): Int {
        return mMaxValue
    }

    fun isFinished(): Boolean {
        return mProgress == mMaxValue
    }

    fun resetTask(){
        mProgress = 0
    }

    override fun onTaskRemoved(rootIntent: Intent){
        println("onTaskRemoved")
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        println("Service onDestroy")
        super.onDestroy()
    }
}