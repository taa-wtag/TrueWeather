package com.rektstudios.trueweather.domain.data

import com.rektstudios.trueweather.domain.util.WeatherConditionMapperUtil

data class CityCardData(
    val name: String = "",
    val fullDate: String = "",
    val temp: String = "",
    val apparentTemp: String = "",
    val condition: String = "",
    val imageUrl: String = "",
    val backgroundColor: Int,
) {
    var cityName = name.substringBefore(",")
    var countryName = name.substringAfter(",")
    var mediumCondition = WeatherConditionMapperUtil.getMediumCondition(condition)
    var shortCondition = WeatherConditionMapperUtil.getShortCondition(condition)
}
