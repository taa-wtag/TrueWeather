package com.rektstudios.trueweather.data.reponse.weather

import com.google.gson.annotations.SerializedName

data class DailyForecastData(
    @SerializedName("date")
    val dateString: String?,
    @SerializedName("date_epoch")
    val dateEpoch: Int?,
    @SerializedName("day")
    val dailyWeatherData: DailyWeatherData?,
    @SerializedName("hour")
    val hourlyWeatherDataList: List<HourlyWeatherData>?
)