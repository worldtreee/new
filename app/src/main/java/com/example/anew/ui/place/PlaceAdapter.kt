package com.example.anew.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout

import androidx.recyclerview.widget.RecyclerView
import com.example.anew.R
import com.example.anew.logic.model.Place
import com.example.anew.ui.weather.WeatherActivity

private val WeatherActivity.drawerLayout: DrawerLayout
    get() {
        return findViewById(R.id.drawerLayout)
    }

//PlaceAdapter与RecyclerView结合使用
//以便在界面中显示地点列表的项视图，并根据数据源（地点列表）进行更新
//传入PlaceFragment 就可以调用PlaceFragment所对应的PlaceViewModel
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) :
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        //从搜索城市界面跳转到天气界面
        val holder = ViewHolder(view)

        //给place_item.xml的最外层布局注册了一个点击事件监听器
        holder.itemView.setOnClickListener {
            //点击事件中获取当前点击项的经纬度坐标和地区名称
            val position = holder.adapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if (activity is WeatherActivity) {
                //如果是在WeatherActivity中
                //那么就关闭滑动菜单,给WeatherViewMode赋值新的经纬度坐标和地区名称
                //然后刷新城市的天气信息

                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            }
            else{
                //把它们传入Intent
                val intent = Intent(parent.context, WeatherActivity::class.java).
                    //用上次保留的place信息
                apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                //调用Fragment的startActivity()方法启动WeatherActivity
                fragment.startActivity(intent)
                fragment.activity?.finish()
            }
            //跳转到WeatherActivity之前，先调用PlaceViewModel的savePlace()方法来存储选中的城市
            fragment.viewModel.savePlace(place)
        }
        return holder
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