package com.rektstudios.trueweather.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.databinding.ItemCityCardAddBinding
import com.rektstudios.trueweather.databinding.ItemCityCardMainBinding
import javax.inject.Inject

private const val VIEW_TYPE_CITY = 0
private const val VIEW_TYPE_ADD = 1
class CityCardAdapter @Inject constructor(
    private val glide: RequestManager
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var navigateToCityFragment: ((View) -> Unit) = {}
    inner class AddCardViewHolder(private val binding: ItemCityCardAddBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(){
            binding.buttonAddCity.setOnClickListener ( navigateToCityFragment )
        }
    }
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_ADD
            else -> VIEW_TYPE_CITY
        }
    }

    private val differ = AsyncListDiffer(this, CityItemDiffCallback())

    var cityItems: List<Pair<CityItem,HourlyWeatherItem?>>
        get() = differ.currentList
        set(value) = differ.submitList(listOf(Pair(CityItem(),null))+value.reversed())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_ADD -> {
                val binding = ItemCityCardAddBinding.inflate(LayoutInflater.from(parent.context), parent,false)
                AddCardViewHolder(binding)
            }
            else -> {
                val binding = ItemCityCardMainBinding.inflate(LayoutInflater.from(parent.context), parent,false)
                CityCardViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int {
        return cityItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is CityCardViewHolder -> holder.bind(cityItems[position].first,cityItems[position].second,glide)
            is AddCardViewHolder -> holder.bind()
        }
    }
}