package com.rektstudios.trueweather.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.databinding.ItemWeatherHourBinding
import javax.inject.Inject

class WeatherHourAdapter @Inject constructor(
    private val glide: RequestManager
):RecyclerView.Adapter<WeatherHourItemViewHolder>() {

    private val differ = AsyncListDiffer(this, WeatherHourAdapterDiffCallback())

    var hourlyWeatherItems: List<HourlyWeatherItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHourItemViewHolder {
        val binding = ItemWeatherHourBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return WeatherHourItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hourlyWeatherItems.size
    }

    override fun onBindViewHolder(holder: WeatherHourItemViewHolder, position: Int) {
        holder.bind(hourlyWeatherItems[position],glide, position)
    }
}