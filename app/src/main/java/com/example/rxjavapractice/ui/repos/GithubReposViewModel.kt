package com.example.rxjavapractice.ui.repos

import com.example.rxjavapractice.base.BaseViewModel
import com.example.rxjavapractice.data.repository.GithubRepository
import com.example.rxjavapractice.entity.GithubRepo
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class GithubReposViewModel(
    private val repository: GithubRepository =
        GithubRepository()
) : BaseViewModel() {

    private val searchSubject = BehaviorSubject.createDefault("" to false)
    val loadingState = PublishSubject.create<Boolean>()
    val reposState = PublishSubject.create<List<GithubRepo>>()

    fun onCreate() {
        compositeDisposable += searchSubject
            .debounce(400 , TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext{ loadingState.onNext(it.second) }
            .observeOn(Schedulers.io())
            .switchMapSingle {
                if (it.first.isEmpty()) Single.just(emptyList())
                else repository.searchGithubRepos(it.first) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext{loadingState.onNext(false)}
            .subscribe{reposState.onNext(it)}
    }

    fun searchGithubRepos(search : String){
        searchSubject.onNext(search to true)
    }

    fun searchGithubRepos() {
        searchSubject.onNext(searchSubject.value!!.first to false)
    }
}