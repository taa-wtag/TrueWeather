package com.rektstudios.trueweather.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rektstudios.trueweather.domain.data.HourlyWeatherCardData
import com.rektstudios.trueweather.domain.util.WeatherConditionMapperUtil

@Entity(
    tableName = "hourly_weather_table",
    indices = [Index(value = ["cityName", "timeEpoch"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["cityName"],
            childColumns = ["cityName"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class HourlyWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String,
    val timeEpoch: Long,
    val timeString: String? = null,
    val tempC: Double? = null,
    val tempF: Double? = null,
    val feelsLikeC: Double? = null,
    val feelsLikeF: Double? = null,
    val visKm: Double? = null,
    val visMiles: Double? = null,
    val windKph: Double? = null,
    val windMph: Double? = null,
    val humidity: Int? = null,
    val isDay: Int? = null,
    val conditionText: String? = null,
    val imageUrl: String? = null,
) : Weather

fun HourlyWeatherEntity.toWeatherHourCardData() =
    HourlyWeatherCardData(
        timeInHours = timeString?.substringAfter(" ").orEmpty(),
        condition = conditionText?.let { WeatherConditionMapperUtil.getShortCondition(it) }.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
    )

sealed interface Weather
