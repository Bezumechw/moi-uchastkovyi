package com.example.moiuchastkoviy

import android.app.Application
import com.example.moiuchastkoviy.retrofit.MyCopApi
import com.example.moiuchastkoviy.retrofit.RetrofitClient

class App : Application() {
    companion object{
//        private
//        const val BASE_URL = "http://my-cop.sunrisetest.site/"
//        public
        const val BASE_URL = "http://212.109.197.239/"
        private var api: MyCopApi? = null

        fun getClient(): MyCopApi {
            if (api == null) {
                api = RetrofitClient.create(BASE_URL).create(MyCopApi::class.java)
            }
            return api!!
        }
    }
}