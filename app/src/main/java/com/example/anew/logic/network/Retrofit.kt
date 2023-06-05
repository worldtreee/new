package com.example.anew.logic.network

import com.example.anew.SunnyWeatherApplication
import com.example.anew.logic.model.DailyResponse
import com.example.anew.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


//用于访问天气信息API的Retrofit接口
//使用@GET注解来声明要访问的API接口
//使用了@Path注解来向请求接口中动态传入经纬度的坐标
interface WeatherService {
    //getRealtimeWeather()方法用于获取实时的天气信息
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String):
            Call<RealtimeResponse>
    //getDailyWeather()方法用于获取未来的天气信息
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String):
            Call<DailyResponse>
}
//这两个方法的返回值分别被声明成了Call<RealtimeResponse>和Call<DailyResponse>
//对应了刚刚定义好的两个数据模型类