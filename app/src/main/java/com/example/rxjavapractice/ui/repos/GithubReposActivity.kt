package com.example.rxjavapractice.ui.repos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rxjavapractice.R
import com.example.rxjavapractice.base.BaseViewModel
import com.example.rxjavapractice.base.BaseViewModelActivity
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_github_repos.*

class GithubReposActivity : BaseViewModelActivity() {
    override val viewModel: GithubReposViewModel by lazy {
        GithubReposViewModel()
    }

    private val adapter : GithubReposAdapter by lazy {
        GithubReposAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_repos)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = this.adapter
        refreshLayout.setOnRefreshListener {
            viewModel.searchGithubRepos()
        }

        searchText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(text: Editable?) {
                viewModel.searchGithubRepos(text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        compositeDisposable += viewModel.loadingState.subscribe() {
            if (it) showLoading() else hideLoading()
        }

        compositeDisposable += viewModel.reposState.subscribe() {
            adapter.items = it
        }
        viewModel.onCreate()
    }

    private fun showLoading(){
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        loading.visibility = View.GONE
        refreshLayout.isRefreshing = false
    }
}
