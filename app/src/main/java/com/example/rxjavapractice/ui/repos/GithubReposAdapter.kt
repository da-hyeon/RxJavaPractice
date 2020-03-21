package com.example.rxjavapractice.ui.repos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjavapractice.R
import com.example.rxjavapractice.entity.GithubRepo
import com.example.rxjavapractice.ui.repo.GithubRepoActivity
import kotlinx.android.synthetic.main.item_github_repo.view.*

class GithubReposAdapter : RecyclerView.Adapter<GithubReposAdapter.RepoViewHolder>() {

    var items: List<GithubRepo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_github_repo, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GithubReposAdapter.RepoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repo: GithubRepo) {
            with(itemView) {
                repoName.text = repo.name
                repoDescription.text = repo.description
                repoStar.setImageResource(
                    if (repo.star) R.drawable.baseline_star_24
                    else R.drawable.baseline_star_border_24
                )
                setOnClickListener {
                    GithubRepoActivity.start(context, repo)
                }
            }
        }
    }
}