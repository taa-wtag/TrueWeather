package com.rektstudios.trueweather.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.rektstudios.trueweather.data.local.WeatherHourItem

class WeatherHourAdapterDiffCallback: DiffUtil.ItemCallback<WeatherHourItem>() {
    override fun areItemsTheSame(oldItem: WeatherHourItem, newItem: WeatherHourItem): Boolean {
        return oldItem.timeEpoch == newItem.timeEpoch
    }
    override fun areContentsTheSame(oldItem: WeatherHourItem, newItem: WeatherHourItem): Boolean {
        return oldItem.tempC == newItem.tempC && oldItem.timeEpoch == newItem.timeEpoch
    }
}