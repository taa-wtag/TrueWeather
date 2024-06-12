package com.rektstudios.trueweather.presentation.adapters

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.databinding.ItemCityCardFragmentBinding
import com.rektstudios.trueweather.domain.util.CardBackgroundUtil.setCityItemBackground
import com.rektstudios.trueweather.domain.util.WeatherConditionMapperUtil.Companion.getShortCondition

class CityItemViewHolder(private val binding: ItemCityCardFragmentBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cityItem: CityItem,
        weatherHourItem: WeatherHourItem?,
        glide: RequestManager,
        listener: ((String) -> Unit)?,
        isDeleteButtonVisible: Boolean,
        toggleDeleteButtonVisibility: () -> Unit
    ){
        weatherHourItem?.let {
            binding.textViewCityCardFragmentTemperature.text = weatherHourItem.tempC.toInt().toString()
            binding.textViewCityCardFragmentCondition.text = getShortCondition(weatherHourItem.conditionText)
            glide.load(weatherHourItem.imageUrl).into(binding.imageViewCityCardFragmentCondition)
        }
        binding.textViewCityCardFragmentCityName.text=cityItem.cityName.substringBefore(",")
        binding.textViewCityCardFragmentCountryName.text=cityItem.cityName.substringAfter(", ")
        binding.buttonDeleteCity.setOnClickListener{
            listener?.let { it(cityItem.cityName) }
            toggleDeleteButtonVisibility()
        }
        binding.buttonDeleteCity.visibility = if(isDeleteButtonVisible) VISIBLE else GONE
        binding.cardViewCityItem.setOnLongClickListener {
            toggleDeleteButtonVisibility()
            return@setOnLongClickListener true
        }
        binding.imageViewCityItemBackground.setImageResource(setCityItemBackground(cityItem.backgroundColor))
    }
}