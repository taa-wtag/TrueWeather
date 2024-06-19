package com.rektstudios.trueweather.presentation.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.data.local.DailyWeatherItem
import com.rektstudios.trueweather.databinding.ItemWeatherDayBinding
import com.rektstudios.trueweather.domain.util.DateUtil

class DailyWeatherItemViewHolder(private val binding: ItemWeatherDayBinding): RecyclerView.ViewHolder(binding.root){
    @SuppressLint("SetTextI18n")
    fun bind(dailyWeatherItem: DailyWeatherItem, glide: RequestManager){
        if(dailyWeatherItem.isValid) {
            binding.textViewWeatherDayItemDayOfWeek.text =
                dailyWeatherItem.dateString?.let { DateUtil.getDayOfWeek(it) }
            binding.textViewWeatherDayItemMaxTemp.text =
                dailyWeatherItem.maxTempC?.toInt().toString() + "° / "
            binding.textViewWeatherDayItemMinTemp.text =
                dailyWeatherItem.minTempC?.toInt().toString() + "°"
            glide.load(dailyWeatherItem.imageUrl)
                .into(binding.imageViewWeatherDayCondition)
        }
    }
}