package com.example.moiuchastkoviy.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val TAG = RetrofitClient::class.java.simpleName
object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun create(baseUrl: String): Retrofit {
        if (retrofit == null) {
            val builder: Retrofit.Builder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
            retrofit = builder.build()
        }

        return retrofit!!
    }


    private fun getClient(): OkHttpClient {
        val builder =  OkHttpClient()
            .newBuilder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(logging)

        return builder.build()
    }

}