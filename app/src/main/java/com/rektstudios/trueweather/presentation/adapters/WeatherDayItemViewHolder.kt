package com.rektstudios.trueweather.presentation.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.databinding.ItemWeatherDayBinding
import com.rektstudios.trueweather.domain.util.DateUtil

class WeatherDayItemViewHolder(private val binding: ItemWeatherDayBinding): RecyclerView.ViewHolder(binding.root){
    @SuppressLint("SetTextI18n")
    fun bind(weatherDayItem: WeatherDayItem, glide: RequestManager){
        binding.textViewWeatherDayItemDayOfWeek.text = DateUtil.getDayOfWeek(weatherDayItem.date)
        binding.textViewWeatherDayItemMaxTemp.text = weatherDayItem.maxTempC.toInt().toString()+"° / "
        binding.textViewWeatherDayItemMinTemp.text = weatherDayItem.minTempC.toInt().toString()+"°"
        glide.load("https:"+weatherDayItem.imageUrl).into(binding.imageViewWeatherDayCondition)
    }
}