package com.rektstudios.trueweather.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.data.local.DailyWeatherItem
import com.rektstudios.trueweather.databinding.ItemForecastTextViewBinding
import com.rektstudios.trueweather.databinding.ItemTodayTextViewBinding
import com.rektstudios.trueweather.databinding.ItemWeatherDayBinding
import javax.inject.Inject

private const val VIEW_TYPE_TODAY_TEXT = 0
private const val VIEW_TYPE_WEATHER_HOUR_RECYCLER_VIEW = 1
private const val VIEW_TYPE_FORECAST_TEXT = 2
private const val VIEW_TYPE_WEATHER_DAY_ITEM = 3
class DailyWeatherAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Inject
    lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter
    inner class TodayTextViewHolder(binding: ItemTodayTextViewBinding) : RecyclerView.ViewHolder(binding.root)
    inner class ForecastTextViewHolder(binding: ItemForecastTextViewBinding) : RecyclerView.ViewHolder(binding.root)
    inner class WeatherHourRecyclerViewHolder(recyclerView: RecyclerView) : RecyclerView.ViewHolder(recyclerView)
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_TODAY_TEXT
            1 -> VIEW_TYPE_WEATHER_HOUR_RECYCLER_VIEW
            2 -> VIEW_TYPE_FORECAST_TEXT
            else -> VIEW_TYPE_WEATHER_DAY_ITEM
        }
    }

    private val differ = AsyncListDiffer(this, DailyWeatherAdapterDiffCallback())

    var dailyWeatherItems: List<DailyWeatherItem>
        get() = differ.currentList
        set(value) = differ.submitList(listOf(DailyWeatherItem(0),DailyWeatherItem(),DailyWeatherItem(0)) + value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_TODAY_TEXT -> {
                val binding = ItemTodayTextViewBinding.inflate(LayoutInflater.from(parent.context), parent,false)
                TodayTextViewHolder(binding)
            }
            VIEW_TYPE_WEATHER_HOUR_RECYCLER_VIEW -> {
                WeatherHourRecyclerViewHolder(createRecyclerView(parent.context))
            }
            VIEW_TYPE_FORECAST_TEXT -> {
                val binding = ItemForecastTextViewBinding.inflate(LayoutInflater.from(parent.context), parent,false)
                ForecastTextViewHolder(binding)
            }
            else -> {
                val binding = ItemWeatherDayBinding.inflate(LayoutInflater.from(parent.context), parent,false)
                DailyWeatherItemViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int { return dailyWeatherItems.size  }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TodayTextViewHolder -> {}
            is WeatherHourRecyclerViewHolder -> {}
            is ForecastTextViewHolder -> {}
            is DailyWeatherItemViewHolder -> holder.bind(dailyWeatherItems[position],glide)
        }
    }

    private fun createRecyclerView(context: Context): RecyclerView{
        val recyclerView = RecyclerView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            clipToPadding = false
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        recyclerView.setPadding(10,0,10,0)
        recyclerView.adapter = hourlyWeatherAdapter
        return recyclerView
    }

}