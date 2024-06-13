package com.rektstudios.trueweather.data.reponse.weather

import com.google.gson.annotations.SerializedName

data class DailyWeatherData(
    @SerializedName("avghumidity")
    val avgHumidity: Int?,
    @SerializedName("avgtemp_c")
    val avgTempC: Double?,
    @SerializedName("avgtemp_f")
    val avgTempF: Double?,
    @SerializedName("avgvis_km")
    val avgVisKm: Double?,
    @SerializedName("avgvis_miles")
    val avgVisMiles: Double?,
    @SerializedName("condition")
    val weatherCondition: WeatherCondition?,
    @SerializedName("maxtemp_c")
    val maxTempC: Double?,
    @SerializedName("maxtemp_f")
    val maxTempF: Double?,
    @SerializedName("maxwind_kph")
    val maxWindKph: Double?,
    @SerializedName("maxwind_mph")
    val maxWindMph: Double?,
    @SerializedName("mintemp_c")
    val minTempC: Double?,
    @SerializedName("mintemp_f")
    val minTempF: Double?,
)