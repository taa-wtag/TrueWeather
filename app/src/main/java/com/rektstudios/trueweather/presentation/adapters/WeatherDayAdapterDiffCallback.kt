package com.rektstudios.trueweather.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.rektstudios.trueweather.data.local.WeatherDayItem

class WeatherDayAdapterDiffCallback: DiffUtil.ItemCallback<WeatherDayItem>() {
    override fun areItemsTheSame(oldItem: WeatherDayItem, newItem: WeatherDayItem): Boolean {
        return oldItem.dateEpoch == newItem.dateEpoch
    }
    override fun areContentsTheSame(oldItem: WeatherDayItem, newItem: WeatherDayItem): Boolean {
        return oldItem.avgTempC == newItem.avgTempC && oldItem.dateEpoch == newItem.dateEpoch
    }
}