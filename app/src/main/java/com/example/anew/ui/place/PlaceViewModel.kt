package com.example.anew.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.anew.logic.Repository
import com.example.anew.logic.model.Place

class PlaceViewModel : ViewModel() {
    //首先PlaceViewModel中也定义了一个searchPlaces()方法
    //将传入的搜索参数赋值给了一个searchLiveData对象
    //并使用Transformations的switchMap()方法来观察这个对象
    private val searchLiveData = MutableLiveData<String>()
    //定义了一个placeList集合
    //用于对界面上显示的城市数据进行缓存
    //可以保证它们在手机屏幕发生旋转的时候不会丢失
    val placeList = ArrayList<Place>()
    //现在每当searchPlaces()函数被调用时,switchMap()方法所对应的转换函数就会执行。
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }
    //searchPlaces()
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}
//在转换函数中，我们只需要调用仓库层中定义的searchPlaces()方法就可以发起网络请求
//同时将仓库层返回的LiveData对象转换成一个可供Activity观察的LiveData对象