package com.example.android_app_3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_app_3.api.views.News
import com.example.android_app_3.api.net.NewsAdapter
import com.example.android_app_3.api.net.NewsApiService
import com.example.android_app_3.api.views.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = newsAdapter
    }

    fun onGetNewsButtonClicked(view: View) {
        val query: String = findViewById<EditText>(R.id.editText).text.toString()
        if (query.isNotEmpty()) {
            lifecycleScope.launch {
                try {
                    val response = NewsApiService.create().getNews(query)
                    handleNewsResponse(response)
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error fetching news", e)
                }
            }
        }
    }

    private suspend fun handleNewsResponse(response: NewsResponse) {
        withContext(Dispatchers.Main) {
            if (response.status == "success") {
                val simplifiedList = response.results.map { news ->
                    val truncatedDescription = news.description?.take(250) ?: ""
                    News(news.title, truncatedDescription, news.link)
                }
                newsAdapter.submitList(simplifiedList)
                println(simplifiedList)
            } else {
                Log.e("MainActivity", "Error: ${response.status}")
            }
        }
    }
}
