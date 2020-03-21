package com.example.rxjavapractice.ui.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rxjavapractice.R
import com.example.rxjavapractice.entity.Issue
import kotlinx.android.synthetic.main.item_issue.view.*

class IssueAdapter : RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    var issues: MutableList<Issue> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addIssue(issue : Issue) {
        issues.add(0 , issue)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IssueViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_issue , parent , false)
    )

    override fun getItemCount(): Int = issues.size


    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(issues[position])
    }

    class IssueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(issue: Issue) {
            with(itemView) {
                val number = "#${issue.number}"
                issueNumber.text = number
                issueTitle.text = issue.title
                setOnClickListener { }
            }
        }
    }
}
