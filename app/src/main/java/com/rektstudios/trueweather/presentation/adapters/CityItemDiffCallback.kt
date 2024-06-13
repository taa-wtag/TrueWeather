package com.rektstudios.trueweather.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem

class CityItemDiffCallback : DiffUtil.ItemCallback<Pair<CityItem,HourlyWeatherItem?>>() {

    override fun areItemsTheSame(
        oldItem: Pair<CityItem, HourlyWeatherItem?>,
        newItem: Pair<CityItem, HourlyWeatherItem?>
    ): Boolean {
        return oldItem.first.cityName == newItem.first.cityName && oldItem.second?.timeEpoch==newItem.second?.timeEpoch
    }

    override fun areContentsTheSame(
        oldItem: Pair<CityItem, HourlyWeatherItem?>,
        newItem: Pair<CityItem, HourlyWeatherItem?>
    ): Boolean {
        return oldItem.first.cityName == newItem.first.cityName && oldItem.second?.tempC==newItem.second?.tempC && oldItem.second?.timeEpoch==newItem.second?.timeEpoch
    }
}