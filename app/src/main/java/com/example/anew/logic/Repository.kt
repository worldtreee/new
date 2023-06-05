package com.example.anew.logic

import androidx.lifecycle.liveData
import com.example.anew.logic.model.Place
import com.example.anew.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
//作为仓库层的统一封装入口
object Repository {
    //将liveData()函数的线程参数类型指定成了Dispatchers.IO
    //这样代码块中的所有代码就都运行在子线程中了

    //调用了SunnyWeatherNetwork的searchPlaces()函数来搜索城市数据
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                //使用Kotlin内置的Result.success()方法来包装获取的城市数据列表
                Result.success(places)
            } else {
                //使用Result.failure()方法来包装一个异常信息
                Result.failure(RuntimeException("response status is${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        //最后使用一个emit()方法将包装的结果发射出去
        emit(result)
    }
}