package com.example.anew.logic.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(val status: String, val places: List<Place>)
data class Place(val name: String, val location: Location,
                 @SerializedName("formatted_address") val address: String)
data class Location(val lng: String, val lat: String)

//很简单，PlaceResponse.kt文件中定义的类与属性
//完全就是按照15.1节中搜索城市数据接口返回的JSON格式来定义的