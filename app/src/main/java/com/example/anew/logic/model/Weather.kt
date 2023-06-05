package com.example.anew.logic.model


//用于将RealtimeResponse和DailyResponse对象封装起来
class Weather {
    data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)
}