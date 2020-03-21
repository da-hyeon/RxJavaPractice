package com.example.rxjavapractice.util

import com.example.rxjavapractice.BuildConfig
import com.example.rxjavapractice.event.LogoutEvent
import com.example.rxjavapractice.event.RxBus
import retrofit2.HttpException

object ErrorHandler {
    fun handle(t: Throwable){
        if (BuildConfig.DEBUG){
            t.printStackTrace()
            // sendCrash(t)
        }

        if (t is HttpException){
            // 401 익셉션은 토큰이 만료된 애라고 약속을 했다
            if (t.code() == 401) {
                RxBus.post(LogoutEvent())
            }
        }
    }
}