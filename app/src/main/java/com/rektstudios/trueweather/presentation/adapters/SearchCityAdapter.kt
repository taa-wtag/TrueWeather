package com.rektstudios.trueweather.presentation.adapters

import android.view.View
import android.view.ViewGroup
import android.graphics.Typeface
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.rektstudios.trueweather.data.local.CityItem

class SearchCityAdapter: RecyclerView.Adapter<SearchCityAdapter.TextViewHolder>() {
    private val differ = AsyncListDiffer(this, CityNameDiffCallback())
    private var onItemClickListener: ((String) -> Unit)? = null
    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }
    var cityItems: List<CityItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val textView = TextView(parent.context)
        textView.apply {
            id = android.R.id.text1
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(16, 16, 16, 16)
            textSize = 16f
            typeface = Typeface.DEFAULT
        }

        // Return a new instance of the ViewHolder
        return TextViewHolder(textView)
    }

    override fun getItemCount(): Int {
        return cityItems.size
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.textView.text = cityItems[position].cityName
        holder.textView.setOnClickListener{ onItemClickListener?.let { it(cityItems[position].cityName) } }
    }
}