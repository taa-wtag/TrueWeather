package com.rektstudios.trueweather.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rektstudios.trueweather.domain.data.CityCardData
import com.rektstudios.trueweather.domain.util.DateUtil

@Entity(tableName = "city_table", indices = [Index(value = ["cityName"], unique = true)])
data class CityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String,
    val backgroundColor: Int,
)

fun CityEntity.toCityCardData(
    hourlyWeatherEntity: HourlyWeatherEntity?,
    isCelsius: Boolean,
) = CityCardData(
    name = cityName,
    fullDate = hourlyWeatherEntity?.timeString?.let { DateUtil.getFullDate(it) }.orEmpty(),
    temp = (if (isCelsius) hourlyWeatherEntity?.tempC else hourlyWeatherEntity?.tempF)?.toString().orEmpty(),
    apparentTemp = (if (isCelsius) hourlyWeatherEntity?.feelsLikeC else hourlyWeatherEntity?.feelsLikeF)?.toString().orEmpty(),
    condition = hourlyWeatherEntity?.conditionText.orEmpty(),
    imageUrl = hourlyWeatherEntity?.imageUrl.orEmpty(),
    backgroundColor = backgroundColor,
)
