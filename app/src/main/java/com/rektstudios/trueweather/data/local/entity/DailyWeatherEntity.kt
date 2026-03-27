package com.rektstudios.trueweather.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rektstudios.trueweather.domain.data.DailyWeatherData
import com.rektstudios.trueweather.domain.util.DateUtil

@Entity(
    tableName = "daily_weather_table",
    indices = [Index(value = ["cityName", "dateEpoch"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["cityName"],
            childColumns = ["cityName"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class DailyWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val cityName: String,
    val dateEpoch: Long? = null,
    val dateString: String? = null,
    val minTempC: Double? = null,
    val minTempF: Double? = null,
    val maxTempC: Double? = null,
    val maxTempF: Double? = null,
    val avgTempC: Double? = null,
    val avgTempF: Double? = null,
    val avgVisKm: Double? = null,
    val avgVisMiles: Double? = null,
    val maxWindKph: Double? = null,
    val maxWindMph: Double? = null,
    val avgHumidity: Int? = null,
    val conditionText: String? = null,
    val imageUrl: String? = null,
) : Weather

fun DailyWeatherEntity.toDailyWeatherData(isCelsius: Boolean) =
    DailyWeatherData(
        dayOfWeek = dateString?.let { DateUtil.getDayOfWeek(it) }.orEmpty(),
        maxTemp = (if (isCelsius) maxTempC else maxTempF)?.toString().orEmpty(),
        minTemp = (if (isCelsius) minTempC else minTempF)?.toString().orEmpty(),
        imageUrl = imageUrl.orEmpty(),
    )
