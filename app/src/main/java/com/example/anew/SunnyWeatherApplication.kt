package com.example.anew

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

//以将这个令牌值配置在SunnyWeatherApplication中，方便之后的获取
class SunnyWeatherApplication : Application() {
    companion object {
        const val TOKEN = "IxV1akW8crOXdCix"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}