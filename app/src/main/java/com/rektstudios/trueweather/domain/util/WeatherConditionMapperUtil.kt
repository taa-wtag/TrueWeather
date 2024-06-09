package com.rektstudios.trueweather.domain.util

import android.util.Log

class WeatherConditionMapperUtil {
    companion object{
        fun getShortCondition(text: String): String{
            return if(text.contains("cloudy",true)) "Cloudy"
            else if(text.contains("overcast",true)) "Overcast"
            else if(text.contains("rain",true) || text.contains("drizzle",true)) "Rain"
            else if(text.contains("snow",true)) "Snow"
            else if(text.contains("fog",true)) "Fog"
            else if(text.contains("mist",true)) "Mist"
            else if(text.contains("clear",true)) "Clear"
            else if(text.contains("sunny",true)) "Sunny"
            else if(text.contains("ice",true)) "Hail"
            else if(text.contains("sleet",true)) "Sleet"
            else if(text.contains("blizzard",true)) "Blizzard"
            else if(text.contains("thunder",true)) "Thunder"
            else "Text $text"
        }
        fun getMediumCondition(text: String): String{
            return when (text) {
                "Sunny" -> "Sunny"
                "Partly cloudy" -> "Partly Cloudy"
                "Cloudy" -> "Cloudy"
                "Overcast" -> "Overcast"
                "Mist" -> "Mist"
                "Patchy rain possible" -> "Patchy Rain"
                "Patchy snow possible" -> "Patchy Snow"
                "Patchy sleet possible" -> "Patchy Sleet"
                "Patchy freezing drizzle possible" -> "Patchy F Drizzle"
                "Thundery outbreaks possible" -> "Thunder"
                "Blowing snow" -> "Blowing Snow"
                "Blizzard" -> "Blizzard"
                "Fog" -> "Fog"
                "Freezing fog" -> "Freezing Fog"
                "Patchy light drizzle" -> "P,L Drizzle"
                "Light drizzle" -> "Light Drizzle"
                "Freezing drizzle" -> "Freezing Drizzle"
                "Heavy freezing drizzle" -> "H,F Drizzle"
                "Patchy light rain" -> "Patchy L Rain"
                "Light rain" -> "Light Rain"
                "Moderate rain at times" -> "Moderate Rain"
                "Moderate rain" -> "Moderate Rain"
                "Heavy rain at times" -> "Heavy Rain"
                "Heavy rain" -> "Heavy Rain"
                "Light freezing rain" -> "Light F Rain"
                "Moderate or heavy freezing rain" -> "M/H Rain"
                "Light sleet" -> "Light Sleet"
                "Moderate or heavy sleet" -> "M/H Sleet"
                "Patchy light snow" -> "Patchy L Snow"
                "Light snow" -> "Light Snow"
                "Patchy moderate snow" -> "Patchy M Snow"
                "Moderate snow" -> "Moderate Snow"
                "Patchy heavy snow" -> "Patchy H Snow"
                "Heavy snow" -> "Heavy Snow"
                "Ice pellets" -> "Ice Pellets"
                "Light rain shower" -> "L Rain Sh"
                "Moderate or heavy rain shower" -> "M/H Rain Sh"
                "Torrential rain shower" -> "Torrential Rain Sh"
                "Light sleet showers" -> "Light Sleet Sh"
                "Moderate or heavy sleet showers" -> "M/H Sleet Sh"
                "Light snow showers" -> "Light Snow Sh"
                "Moderate or heavy snow showers" -> "M/H Snow Sh"
                "Light showers of ice pellets" -> "L Ice Pellet Sh"
                "Moderate or heavy showers of ice pellets" -> "M/H Ice Pellet Sh"
                "Patchy light rain with thunder" -> "P,L Rain & Thunder"
                "Moderate or heavy rain with thunder" -> "M/H Rain & Thunder"
                "Patchy light snow with thunder" -> "L,P Snow & Thunder"
                "Moderate or heavy snow with thunder" ->"H Snow & Thunder"
                else -> text
            }
        }
    }
}