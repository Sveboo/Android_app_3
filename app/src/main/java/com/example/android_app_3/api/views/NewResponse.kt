package com.example.android_app_3.api.views

import com.example.android_app_3.api.views.News

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val results: List<News>
)