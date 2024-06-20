package com.rektstudios.trueweather.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.rektstudios.trueweather.data.local.DailyWeatherItem

class DailyWeatherAdapterDiffCallback : DiffUtil.ItemCallback<DailyWeatherItem>() {
    override fun areItemsTheSame(oldItem: DailyWeatherItem, newItem: DailyWeatherItem): Boolean {
        return (oldItem.dateEpoch == newItem.dateEpoch && oldItem.avgTempC == newItem.avgTempC)
                || oldItem.dateEpoch == null
                || newItem.dateEpoch === null
    }

    override fun areContentsTheSame(oldItem: DailyWeatherItem, newItem: DailyWeatherItem): Boolean {
        return oldItem.avgTempC == newItem.avgTempC
                && oldItem.dateEpoch == newItem.dateEpoch
    }
}