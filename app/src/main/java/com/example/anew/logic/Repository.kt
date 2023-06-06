package com.example.anew.logic

import androidx.lifecycle.liveData
import com.example.anew.logic.dao.PlaceDao
import com.example.anew.logic.model.Place
import com.example.anew.logic.model.Weather
import com.example.anew.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext


//作为仓库层的统一封装入口
object Repository {



//    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
//        val result = try {
//            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
//            if (placeResponse.status == "ok") {
//                val places = placeResponse.places
//
//                Result.success(places)
//            } else {
//
//                Result.failure(RuntimeException("response status is${placeResponse.status}"))
//            }
//        } catch (e: Exception) {
//            Result.failure<List<Place>>(e)
//        }
//        //最后使用一个emit()方法将包装的结果发射出去
//        emit(result)
//    }
    //将liveData()  //fire()函数的线程参数类型指定成了Dispatchers.IO
    //这样代码块中的所有代码就都运行在子线程中了
    //调用了SunnyWeatherNetwork的searchPlaces()函数来搜索城市数据
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            //使用Kotlin内置的Result.success()方法来包装获取的城市数据列表
            Result.success(places)
        } else {
            //使用Result.failure()方法来包装一个异常信息
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }



//    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
//        val result = try {

//            coroutineScope {
//                val deferredRealtime = async {
//                    SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
//                }
//                val deferredDaily = async {
//                    SunnyWeatherNetwork.getDailyWeather(lng, lat)
//                }

//                val realtimeResponse = deferredRealtime.await()
//                val dailyResponse = deferredDaily.await()
//

//                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
//                    val weather = Weather.Weather(
//                        realtimeResponse.result.realtime,
//                        dailyResponse.result.daily
//                    )
//                    Result.success(weather)

//                } else {
//                    Result.failure(
//                        RuntimeException(
//                            "realtime response status is ${realtimeResponse.status}" +
//                                    "daily response status is ${dailyResponse.status}"
//                        )
//                    )
//                }
//            }
//        } catch (e: Exception) {
//            Result.failure<Weather>(e)
//        }
//        emit(result)
//    }
//}

    //将liveData()  //fire()函数的线程参数类型指定成了Dispatchers.IO
    //提供了一个refreshWeather()方法用来刷新天气信息
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        //使用coroutineScope函数创建了一个协程作用域。
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            //await()
            //只有在两个网络请求都成功响应之后，才会进一步执行程序
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            //如果它们的响应状态都是ok
            //那么就将Realtime和Daily对象取出并封装到一个Weather对象中
            //然后使用Result.success()方法来包装这个Weather对象
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather.Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            //否则就使用Result.failure()方法来包装一个异常信息
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }
//    fire()函数的内部会先调用一下liveData()函数
//    然后在liveData()函数的代码块中统一进行了try catch 处理，
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
    // 做了一层接口封装
    // 逻辑是和PlaceViewModel相关的 因此我们还得在PlaceViewModel中再进行一层封装才行
    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}