package com.example.rxjavapractice.base

import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel {
    protected val compositeDisposable = CompositeDisposable()

    fun onDestroy(){
        compositeDisposable.dispose()
    }
}