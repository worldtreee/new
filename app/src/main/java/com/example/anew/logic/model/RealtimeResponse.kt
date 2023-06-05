package com.example.anew.logic.model

import com.google.gson.annotations.SerializedName
//json格式
//{
//    "status": "ok",
//    "result": {
//    "realtime": {
//    "temperature": 23.16,
//    "skycon": "WIND",
//    "air_quality": {
//    "aqi": { "chn": 17.0 }
//}
//}
//}
//}

//RealtimeResponse 是顶层数据类，包含两个属性：
//status：表示请求的状态，为一个字符串类型。
//result：表示响应的结果，为一个 Result 类型的对象。

data class RealtimeResponse(val status: String, val result: Result) {
    //Result 是一个嵌套在 RealtimeResponse 中的数据类，包含一个属性：
    //realtime：表示实时天气数据，为一个 Realtime 类型的对象。
    data class Result(val realtime: Realtime)
    //Realtime 是一个嵌套在 Result 中的数据类，包含三个属性：
    //skycon：表示天气状况，为一个字符串类型。
    //temperature：表示温度，为一个浮点数类型。
    //airQuality：表示空气质量，为一个 AirQuality 类型的对象。
    data class Realtime(val skycon: String, val temperature: Float,
                        @SerializedName("air_quality") val airQuality: AirQuality)
    //AirQuality 是一个嵌套在 Realtime 中的数据类，包含一个属性：
    //aqi：表示空气质量指数，为一个 AQI 类型的对象。
    data class AirQuality(val aqi: AQI)
    //AQI 是一个嵌套在 AirQuality 中的数据类，包含一个属性：
    //chn：表示中国标准的空气质量指数，为一个浮点数类型
    data class AQI(val chn: Float)
}