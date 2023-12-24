package com.example.android_app_3.api.net

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsApiService {

    private const val BASE_URL = "https://newsdata.io/api/1/"
    private const val API_KEY = "pub_3534979708748b510c9907e4cc01eed091d67"

    fun create(): NewsApiInterface {
        val client = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NewsApiInterface::class.java)
    }

    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apiKey", API_KEY)
            .build()

        val request = original.newBuilder()
            .url(url)
            .build()

        chain.proceed(request)
    }
}
