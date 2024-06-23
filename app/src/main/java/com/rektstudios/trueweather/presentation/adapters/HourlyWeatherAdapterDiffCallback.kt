package com.rektstudios.trueweather.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.rektstudios.trueweather.data.local.HourlyWeatherItem

class HourlyWeatherAdapterDiffCallback : DiffUtil.ItemCallback<HourlyWeatherItem>() {
    override fun areItemsTheSame(oldItem: HourlyWeatherItem, newItem: HourlyWeatherItem): Boolean {
        return oldItem.timeEpoch == newItem.timeEpoch
    }

    override fun areContentsTheSame(
        oldItem: HourlyWeatherItem, newItem: HourlyWeatherItem
    ): Boolean {
        return oldItem.tempC == newItem.tempC && oldItem.timeEpoch == newItem.timeEpoch
    }
}