package com.rektstudios.trueweather.domain.util

import com.rektstudios.trueweather.R

object CardBackgroundUtil {
    fun setCityCardBackground(value: Int): Int {
        return when(value){
            1 -> R.drawable.city_card_background_1
            2 -> R.drawable.city_card_background_2
            3 -> R.drawable.city_card_background_3
            4 -> R.drawable.city_card_background_4
            5 -> R.drawable.city_card_background_5
            6 -> R.drawable.city_card_background_6
            else -> R.drawable.city_card_background_1
        }
    }

    fun setCityItemBackground(value: Int): Int {
        return when(value){
            1 -> R.drawable.city_item_background_1
            2 -> R.drawable.city_item_background_2
            3 -> R.drawable.city_item_background_3
            4 -> R.drawable.city_item_background_4
            5 -> R.drawable.city_item_background_5
            6 -> R.drawable.city_item_background_6
            else -> R.drawable.city_item_background_1
        }
    }
}