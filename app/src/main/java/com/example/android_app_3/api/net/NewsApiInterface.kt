package com.example.android_app_3.api.net

import com.example.android_app_3.api.views.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {
    @GET("news")
    suspend fun getNews(
        @Query("q") query: String
    ): NewsResponse
}
