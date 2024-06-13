package com.rektstudios.trueweather.presentation.adapters

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.R
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.databinding.ItemWeatherHourBinding
import com.rektstudios.trueweather.domain.util.WeatherConditionMapperUtil.Companion.getShortCondition

class WeatherHourItemViewHolder(private val binding: ItemWeatherHourBinding): RecyclerView.ViewHolder(binding.root){
    @SuppressLint("ResourceAsColor")
    fun bind(hourlyWeatherItem: HourlyWeatherItem, glide: RequestManager, position: Int){
        if(hourlyWeatherItem.isValid && hourlyWeatherItem.isLoaded) {
            binding.textViewWeatherHourCardTime.text = hourlyWeatherItem.timeString?.substringAfter(" ")
            binding.textViewWeatherHourCardCondition.text = hourlyWeatherItem.conditionText?.let { getShortCondition(it) }
            binding.imageViewWeatherHourCardCondition
            glide.load(hourlyWeatherItem.imageUrl)
                .into(binding.imageViewWeatherHourCardCondition)
            binding.weatherHourCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (position == 0) R.color.weather_hour_card_selected_background else R.color.white
                )
            )
            binding.textViewWeatherHourCardCondition.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (position == 0) R.color.weather_hour_card_selected_text else R.color.light_grey
                )
            )
            binding.textViewWeatherHourCardTime.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (position == 0) R.color.weather_hour_card_selected_text else R.color.light_grey
                )
            )
        }
    }
}