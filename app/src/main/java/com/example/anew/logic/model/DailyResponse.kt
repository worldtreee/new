package com.example.anew.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

//json格式
//{
//    "status": "ok",
//    "result": {
//    "daily": {
//    "temperature": [ {"max": 25.7, "min": 20.3}, ... ],
//    "skycon": [ {"value": "CLOUDY", "date":"2019-10-20T00:00+08:00"}, ... ],
//    "life_index": {
//    "coldRisk": [ {"desc": "易发"}, ... ],
//    "carWashing": [ {"desc": "适宜"}, ... ],
//    "ultraviolet": [ {"desc": "无"}, ... ],
//    "dressing": [ {"desc": "舒适"}, ... ]
//            }
//        }
//    }
//}

//DailyResponse 是顶层数据类，包含两个属性：
//status：表示请求的状态，为一个字符串类型。
//result：表示响应的结果，为一个 Result 类型的对象。
data class DailyResponse(val status: String, val result: Result) {
    //Result 是一个嵌套在 DailyResponse 中的数据类，包含一个属性：
    //daily：表示每日天气数据，为一个 Daily 类型的对象。
    data class Result(val daily: Daily)
    //Daily 是一个嵌套在 Result 中的数据类，包含三个属性：
    //temperature：表示温度数据列表，为一个 List<Temperature> 类型的对象。
    //skycon：表示天气状况数据列表，为一个 List<Skycon> 类型的对象。
    //lifeIndex：表示生活指数数据，为一个 LifeIndex 类型的对象。
    data class Daily(val temperature: List<Temperature>, val skycon: List<Skycon>,
                     @SerializedName("life_index") val lifeIndex: LifeIndex)
    //Temperature 是一个嵌套在 Daily 中的数据类，包含两个属性：
    //max：表示最高温度，为一个浮点数类型。
    //min：表示最低温度，为一个浮点数类型。
    data class Temperature(val max: Float, val min: Float)
    //Skycon 是一个嵌套在 Daily 中的数据类，包含两个属性：
    //value：表示天气状况的值，为一个字符串类型。
    //date：表示日期，为一个 Date 类型的对象。
    data class Skycon(val value: String, val date: Date)
    //LifeIndex 是一个嵌套在 Daily 中的数据类，包含四个属性：
    //coldRisk：表示寒冷风险指数，为一个 List<LifeDescription> 类型的对象。
    //carWashing：表示洗车指数，为一个 List<LifeDescription> 类型的对象。
    //ultraviolet：表示紫外线指数，为一个 List<LifeDescription> 类型的对象。
    //dressing：表示穿衣指数，为一个 List<LifeDescription> 类型的对象。
    data class LifeIndex(val coldRisk: List<LifeDescription>, val carWashing:
    List<LifeDescription>, val ultraviolet: List<LifeDescription>,
                         val dressing: List<LifeDescription>)
    //LifeDescription 是一个嵌套在 LifeIndex 中的数据类，包含一个属性：
    //desc：表示描述信息，为一个字符串类型。
    data class LifeDescription(val desc: String)
}