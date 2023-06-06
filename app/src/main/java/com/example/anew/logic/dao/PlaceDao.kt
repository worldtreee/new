package com.example.anew.logic.dao

import com.example.anew.logic.model.Place
import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.example.anew.SunnyWeatherApplication

//持久化技术 实现记录选中城市的功能
//使用SharedPreferences存储
object PlaceDao {
    //存储   savePlace()方法用于将Place对象存储到SharedPreferences 文件中
    //先通过GSON将Place对象转成一个JSON字符串，然后用字符串存储的方式来保存数据
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }
    //读取 getSavedPlace()方法
    //先将JSON字符串从SharedPreferences文件中读取出来
    //然后再通过GSON将JSON字符串解析成Place对象并返回。
    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }
    //isPlaceSaved()方法 用于判断是否有数据已被存储
    fun isPlaceSaved() = sharedPreferences().contains("place")
    private fun sharedPreferences() = SunnyWeatherApplication.context.
    getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}
//PlaceDao封装好了之后，接下来我们就可以在仓库层进行实现了 到Repository