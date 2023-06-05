package com.example.anew.logic.network

import com.example.anew.SunnyWeatherApplication
import com.example.anew.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


//定义一个用于访问彩云天气城市搜索API的Retrofit接口，
interface PlaceService {
    //声明了@GET注解,当调用searchPlaces()方法的时候
    //Retrofit就会自动发起一条GET请求，去访问@GET注解中配置的地址。
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    //searchPlaces()方法的返回值被声明成了Call<PlaceResponse>
    //这样Retrofit就会将服务器返回的JSON数据自动解析成PlaceResponse对象了。
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}