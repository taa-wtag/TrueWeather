package com.rektstudios.trueweather.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.rektstudios.trueweather.data.local.CityItem

class CityNameDiffCallback: DiffUtil.ItemCallback<CityItem>() {

    override fun areItemsTheSame(oldItem: CityItem, newItem: CityItem): Boolean {
        return oldItem.cityName==newItem.cityName
    }

    override fun areContentsTheSame(oldItem: CityItem, newItem: CityItem): Boolean {
        return oldItem.cityName==newItem.cityName
    }

}
