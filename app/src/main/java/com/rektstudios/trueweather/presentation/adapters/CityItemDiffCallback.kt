package com.rektstudios.trueweather.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherHourItem

class CityItemDiffCallback : DiffUtil.ItemCallback<Pair<CityItem,WeatherHourItem?>>() {

    override fun areItemsTheSame(
        oldItem: Pair<CityItem, WeatherHourItem?>,
        newItem: Pair<CityItem, WeatherHourItem?>
    ): Boolean {
        return oldItem.first.cityName == newItem.first.cityName && oldItem.second?.timeEpoch==newItem.second?.timeEpoch
    }

    override fun areContentsTheSame(
        oldItem: Pair<CityItem, WeatherHourItem?>,
        newItem: Pair<CityItem, WeatherHourItem?>
    ): Boolean {
        return oldItem.first.cityName == newItem.first.cityName && oldItem.second?.tempC==newItem.second?.tempC && oldItem.second?.timeEpoch==newItem.second?.timeEpoch
    }
}