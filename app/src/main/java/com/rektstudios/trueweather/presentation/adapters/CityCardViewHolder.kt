package com.rektstudios.trueweather.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.databinding.ItemCityCardMainBinding
import com.rektstudios.trueweather.domain.util.CardBackgroundUtil.setCityCardBackground
import com.rektstudios.trueweather.domain.util.DateUtil.Companion.getFullDate
import com.rektstudios.trueweather.domain.util.WeatherConditionMapperUtil.Companion.getMediumCondition

class CityCardViewHolder(private val binding: ItemCityCardMainBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(cityItem: CityItem, weatherHourItem: WeatherHourItem?, glide: RequestManager) {
        weatherHourItem?.let {
            if(weatherHourItem.isValid) {
                binding.textViewCityCardDate.text = getFullDate(it.time)
                binding.textViewCityCardTemperature.text = it.feelsLikeC.toInt().toString()
                binding.textViewCityCardCondition.text = getMediumCondition(it.conditionText)
                glide.load(it.imageUrl).into(binding.imageViewCityCardCondition)
            }
        }
        binding.textViewCityCardCityName.text=cityItem.cityName.substringBefore(",")
        binding.textViewCityCardCountryName.text=cityItem.cityName.substringAfter(", ")
        binding.imageViewCityCardBackground.setImageResource(setCityCardBackground(cityItem.backgroundColor))
    }
}