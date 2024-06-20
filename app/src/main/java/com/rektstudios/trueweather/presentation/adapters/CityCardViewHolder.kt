package com.rektstudios.trueweather.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.databinding.ItemCityCardMainBinding
import com.rektstudios.trueweather.domain.util.CardBackgroundUtil.setCityCardBackground
import com.rektstudios.trueweather.domain.util.DateUtil.Companion.getFullDate
import com.rektstudios.trueweather.domain.util.WeatherConditionMapperUtil.Companion.getMediumCondition

class CityCardViewHolder(private val binding: ItemCityCardMainBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(cityItem: CityItem, hourlyWeatherItem: HourlyWeatherItem?, glide: RequestManager) {
        hourlyWeatherItem?.let {
            if (it.isValid && it.isLoaded) {
                binding.textViewCityCardDate.text = it.timeString?.let { it1 -> getFullDate(it1) }
                binding.textViewCityCardTemperature.text = it.feelsLikeC?.toInt().toString()
                binding.textViewCityCardCondition.text =
                    it.conditionText?.let { it1 -> getMediumCondition(it1) }
                glide.load(it.imageUrl).into(binding.imageViewCityCardCondition)
            }
        }
        if (cityItem.isValid && cityItem.isLoaded) {
            binding.textViewCityCardCityName.text = cityItem.cityName?.substringBefore(",")
            binding.textViewCityCardCountryName.text = cityItem.cityName?.substringAfter(", ")
            binding.imageViewCityCardBackground.setImageResource(
                setCityCardBackground(
                    cityItem.backgroundColor ?: 1
                )
            )
        }
    }
}