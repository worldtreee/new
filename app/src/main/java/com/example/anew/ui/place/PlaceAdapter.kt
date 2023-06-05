package com.example.anew.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.anew.R
import com.example.anew.logic.model.Place

//PlaceAdapter与RecyclerView结合使用
//以便在界面中显示地点列表的项视图，并根据数据源（地点列表）进行更新
class PlaceAdapter(private val fragment: Fragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    // 内部类 ViewHolder，用于持有项视图中的控件引用
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName) // 地点名称的 TextView
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)// 地点地址的 TextView
    }
    //onCreateViewHolder方法用于创建ViewHolder实例并返回。
    //它通过LayoutInflater将项视图的布局文件转换为View对象
    //并将其传递给ViewHolder构造函数
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,
            parent, false)
        return ViewHolder(view)
    }
    //onBindViewHolder方法用于将地点数据绑定到项视图中的控件
    //根据项的位置，获取对应的地点对象
    //并将地点的名称和地址设置到对应的TextView控件中
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }
    // 返回地点列表的大小，确定RecyclerView中项的数量
    override fun getItemCount() = placeList.size
}