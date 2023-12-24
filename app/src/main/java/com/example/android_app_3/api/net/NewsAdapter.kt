package com.example.android_app_3.api.net

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_app_3.R
import com.example.android_app_3.api.views.News

class NewsAdapter : ListAdapter<News, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.description)
        private val buttonOpenLink: Button = itemView.findViewById(R.id.readMore)

        fun bind(news: News) {
            titleTextView.text = news.title
            descriptionTextView.text = news.description
            buttonOpenLink.setOnClickListener {
                openLinkInBrowser(news.link)
            }
        }

        private fun openLinkInBrowser(link: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            itemView.context.startActivity(intent)
        }
    }

    private class NewsDiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }
}
