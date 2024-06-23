package com.rektstudios.trueweather.presentation.adapters

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.databinding.ItemCityCardFragmentBinding
import com.rektstudios.trueweather.domain.util.CardBackgroundUtil.setCityItemBackground
import com.rektstudios.trueweather.domain.util.WeatherConditionMapperUtil.Companion.getShortCondition

class CityItemViewHolder(private val binding: ItemCityCardFragmentBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        cityItem: CityItem,
        hourlyWeatherItem: HourlyWeatherItem?,
        glide: RequestManager,
        listener: ((String) -> Unit)?,
        isDeleteButtonVisible: Boolean,
        toggleDeleteButtonVisibility: () -> Unit
    ) {
        hourlyWeatherItem?.let {
            if (it.isValid && it.isLoaded) {
                binding.textViewCityCardFragmentTemperature.text = it.feelsLikeC?.toInt().toString()
                binding.textViewCityCardFragmentCondition.text =
                    it.conditionText?.let { it1 -> getShortCondition(it1) }
                glide.load(it.imageUrl).into(binding.imageViewCityCardFragmentCondition)
            }
        }
        if (cityItem.isValid && cityItem.isLoaded) {
            binding.textViewCityCardFragmentCityName.text = cityItem.cityName?.substringBefore(",")
            binding.textViewCityCardFragmentCountryName.text =
                cityItem.cityName?.substringAfter(", ")
            binding.buttonDeleteCity.setOnClickListener {
                listener?.let { cityItem.cityName?.let { it1 -> it(it1) } }
                toggleDeleteButtonVisibility()
            }
            binding.buttonDeleteCity.visibility = if (isDeleteButtonVisible) VISIBLE else GONE
            binding.cardViewCityItem.setOnLongClickListener {
                toggleDeleteButtonVisibility()
                return@setOnLongClickListener true
            }
            binding.imageViewCityItemBackground.setImageResource(
                setCityItemBackground(
                    cityItem.backgroundColor ?: 1
                )
            )
        }
    }
}