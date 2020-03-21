package com.example.rxjavapractice.base

import android.app.Application
import android.content.Context
import com.example.rxjavapractice.event.LogoutEvent
import com.example.rxjavapractice.event.RxBus
import com.example.rxjavapractice.util.ErrorHandler
import com.example.rxjavapractice.util.extension.showToast
import com.facebook.stetho.Stetho
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins

class BaseApplication : Application() {
    companion object {
        lateinit var appContext: Context
        const val TAG = "RxJavaPractice"
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Stetho.initializeWithDefaults(this)
        setErrorHandler()
    }

    private fun setErrorHandler() {
        // onError 가 없거나, onError에서 또 Exception이 나면 오는애
        RxJavaPlugins.setErrorHandler{
            ErrorHandler.handle(it)
        }

        RxBus.observe()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                when(it){
                    is LogoutEvent ->
                        showToast("Logout");
                }
            }
    }

}