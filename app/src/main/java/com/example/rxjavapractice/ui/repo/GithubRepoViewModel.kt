package com.example.rxjavapractice.ui.repo

import com.example.rxjavapractice.base.BaseViewModel
import com.example.rxjavapractice.data.repository.GithubRepository
import com.example.rxjavapractice.entity.GithubRepo
import com.example.rxjavapractice.entity.Issue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject

class GithubRepoViewModel(
    private val repository: GithubRepository = GithubRepository()
) : BaseViewModel() {
    val repoState = BehaviorSubject.create<GithubRepo>()
    val issuesState = BehaviorSubject.createDefault<List<Issue>>(emptyList())

    fun onCreate(repo : GithubRepo){
        repoState.onNext(repo)
        compositeDisposable += repository.issues(repo.owner.userName , repo.name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(issuesState::onNext)
    }

    fun save(repo: GithubRepo) = repository.save(repo).subscribe()

//    fun onClickStar() {
//        val repo = repoState.value!!
//        val doStar = !repo.star
//        repoState.onNext(
//            repo.copy(
//
//            )
//        )
//    }
}