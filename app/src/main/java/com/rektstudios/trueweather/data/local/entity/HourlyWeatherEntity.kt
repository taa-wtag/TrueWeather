package com.rektstudios.trueweather.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hourly_weather_table",
    indices = [Index(value = ["timeEpoch"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["cityName"],
            childColumns = ["cityName"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
open class HourlyWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String,
    var timeEpoch: Long? = null,
    var timeString: String? = null,
    var tempC: Double? = null,
    var tempF: Double? = null,
    var feelsLikeC: Double? = null,
    var feelsLikeF: Double? = null,
    var visKm: Double? = null,
    var visMiles: Double? = null,
    var windKph: Double? = null,
    var windMph: Double? = null,
    var humidity: Int? = null,
    var isDay: Int? = null,
    var conditionText: String? = null,
    var imageUrl: String? = null,
)
