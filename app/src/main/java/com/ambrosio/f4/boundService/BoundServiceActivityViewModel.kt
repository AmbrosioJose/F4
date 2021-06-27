package com.ambrosio.f4.boundService

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.ambrosio.f4.boundService.MyBoundService
import android.content.ServiceConnection
import android.content.ComponentName
import android.os.IBinder



class BoundServiceActivityViewModel: ViewModel() {

    private val mIsProgressUpdating: MutableLiveData<Boolean> = MutableLiveData()
    private val mBinder: MutableLiveData<MyBoundService.MyBinder?> = MutableLiveData()

    private val serviceConnection = object : ServiceConnection {

        /// `componentName` represents what service is being bound
        /// `iBinder` is the link between the client and the service
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder){
            println("onServiceConnected: connected to service")
            val binder: MyBoundService.MyBinder = iBinder as MyBoundService.MyBinder
            mBinder.postValue(binder)
        }

        override fun onServiceDisconnected(componetName: ComponentName) {
            println("onServiceDisconnected: ")
            mBinder.postValue(null)

        }
    }

    fun getIsProgressUpdating() : LiveData<Boolean>{
        return mIsProgressUpdating
    }

    fun getBinder() : LiveData<MyBoundService.MyBinder?> {
        return mBinder
    }

    fun getServiceConnection(): ServiceConnection{
        return serviceConnection
    }

    fun setIsUpdating(isUpdating: Boolean) {
        mIsProgressUpdating.postValue(isUpdating)
    }
}