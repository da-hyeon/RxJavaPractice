package com.example.rxjavapractice.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    //생성된 모든 Observable 을 안드로이드 라이프사이클에 맞춰 한번에 모두 해제하기 위함
    protected val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onSupportNavigateUp(): Boolean {
        if(!super.onSupportNavigateUp())
            onBackPressed()
        return true
    }
}

abstract class BaseViewModelActivity : BaseActivity() {
    abstract val viewModel : BaseViewModel

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}