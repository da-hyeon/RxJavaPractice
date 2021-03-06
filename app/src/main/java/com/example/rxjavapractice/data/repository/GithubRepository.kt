package com.example.rxjavapractice.data.repository

import com.example.rxjavapractice.data.db.AppDatabase
import com.example.rxjavapractice.data.request.CreateIssueRequest
import com.example.rxjavapractice.entity.GithubRepo
import com.google.gson.reflect.TypeToken
import com.example.rxjavapractice.data.source.ApiManager
import com.example.rxjavapractice.entity.Issue
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class GithubRepository {

    private val api = ApiManager.githubApi
    private val dao = AppDatabase.get().githubDao()

    fun save(repo: GithubRepo) =
        dao.insert(repo)
            .subscribeOn(Schedulers.io())

    fun searchGithubRepos(q: String) =
        api.searchRepos(q)
                //items의 키값으로 되어있는 array로 변경
            .map {
                it.asJsonObject.getAsJsonArray("items")
            }
                //타입을 GithubRepo로 변경
            .map {
                val type = object : TypeToken<List<GithubRepo>>() {}.type
                ApiManager.gson.fromJson(it, type) as List<GithubRepo>
            }.subscribeOn(Schedulers.io())

    fun checkStar(owner: String, repo: String): Completable =
        api.checkStar(owner, repo)
            .subscribeOn(Schedulers.io())

    fun star(owner: String, repo: String): Completable =
        api.star(owner, repo)
            .subscribeOn(Schedulers.io())

    fun unstar(owner: String, repo: String): Completable =
        api.unstar(owner, repo)
            .subscribeOn(Schedulers.io())

    fun issues(owner: String, repo: String): Single<List<Issue>> =
        api.issues(owner, repo)
            .subscribeOn(Schedulers.io())

    fun createIssue(owner: String, repo: String, title: String, body: String): Single<Issue> =
        api.createIssue(owner, repo, CreateIssueRequest(title, body))
            .subscribeOn(Schedulers.io())
}
