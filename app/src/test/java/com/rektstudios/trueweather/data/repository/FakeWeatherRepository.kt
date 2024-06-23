package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.local.DailyWeatherItem
import com.rektstudios.trueweather.data.local.HourlyWeatherItem
import com.rektstudios.trueweather.data.reponse.weather.WeatherCondition
import com.rektstudios.trueweather.data.reponse.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.ForecastData
import com.rektstudios.trueweather.data.reponse.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.DailyForecastData
import com.rektstudios.trueweather.data.reponse.weather.CityData
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse
import com.rektstudios.trueweather.data.reponse.weather.DailyWeatherData
import com.rektstudios.trueweather.data.reponse.weather.HourlyWeatherData
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class FakeWeatherRepository(private val realmDao: IRealmDao) : IWeatherRepository {
    private val locationMap = mutableMapOf(
        Pair(Pair(23.72, 90.41), "Dhaka, Bangladesh"),
        Pair(Pair(26.68, 85.17), "Dhaka, India"),
        Pair(Pair(34.05, -118.24), "Los Angeles, United States"),
        Pair(Pair(13.75, 100.52), "Bangkok, Thailand"),
        Pair(Pair(35.69, 139.69), "Tokyo, Japan"),
    )

    val fakeCurrentWeatherResponses = listOf(
        CurrentWeatherResponse(
            HourlyWeatherData(
                WeatherCondition("//cdn.weatherapi.com/weather/64x64/day/296.png", "Light rain"),
                21.0,
                69.8,
                78,
                1,
                21.0,
                69.8,
                "2024-06-02 14:45",
                1717307488,
                8.0,
                4.0,
                9.0,
                5.6
            )
        ),
        CurrentWeatherResponse(
            HourlyWeatherData(
                WeatherCondition("//cdn.weatherapi.com/weather/64x64/day/116.png", "Partly cloudy"),
                18.0,
                64.4,
                56,
                1,
                18.0,
                64.4,
                "2024-06-02 14:45",
                1717307488,
                10.0,
                6.0,
                24.1,
                15.0
            )
        )
    )
    val fakeForecastResponses = listOf(
        ForecastWeatherResponse(
            HourlyWeatherData(
                WeatherCondition("//cdn.weatherapi.com/weather/64x64/day/296.png", "Light rain"),
                21.0,
                69.8,
                78,
                1,
                21.0,
                69.8,
                "2024-06-02 14:45",
                1717307488,
                8.0,
                4.0,
                9.0,
                5.6
            ),
            ForecastData(
                listOf(
                    DailyForecastData(
                        "2024-06-02",
                        1717286400,
                        DailyWeatherData(
                            71,
                            19.4,
                            66.9,
                            8.3,
                            5.0,
                            WeatherCondition(
                                "//cdn.weatherapi.com/weather/64x64/day/302.png",
                                "Moderate rain"
                            ),
                            22.7,
                            72.8,
                            27.4,
                            17.0,
                            17.2,
                            62.9
                        ),
                        listOf(
                            HourlyWeatherData(
                                WeatherCondition(
                                    "//cdn.weatherapi.com/weather/64x64/day/266.png",
                                    "Light drizzle"
                                ),
                                21.9,
                                71.3,
                                63,
                                1,
                                21.9,
                                71.3,
                                "2024-06-02 15:00",
                                1717308000,
                                2.0,
                                1.0,
                                22.0,
                                13.6
                            )
                        )
                    )
                )
            )
        ),
        ForecastWeatherResponse(
            HourlyWeatherData(
                WeatherCondition("//cdn.weatherapi.com/weather/64x64/day/116.png", "Partly cloudy"),
                18.0,
                64.4,
                56,
                1,
                18.0,
                64.4,
                "2024-06-02 14:45",
                1717307488,
                10.0,
                6.0,
                24.1,
                15.0
            ),
            ForecastData(
                listOf(
                    DailyForecastData(
                        "2024-06-02",
                        1717286400,
                        DailyWeatherData(
                            65,
                            12.1,
                            53.8,
                            10.0,
                            6.0,
                            WeatherCondition(
                                "//cdn.weatherapi.com/weather/64x64/day/113.png",
                                "Sunny"
                            ),
                            17.3,
                            63.1,
                            20.5,
                            12.8,
                            6.6,
                            43.8
                        ),
                        listOf(
                            HourlyWeatherData(
                                WeatherCondition(
                                    "//cdn.weatherapi.com/weather/64x64/night/113.png",
                                    "Clear "
                                ),
                                6.4,
                                43.5,
                                91,
                                1,
                                8.1,
                                46.5,
                                "2024-06-02 15:00",
                                1717308000,
                                10.0,
                                6.0,
                                9.7,
                                6.0
                            )
                        )
                    )
                )
            )
        )
    )

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun getCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse> {
        return if (shouldReturnNetworkError)
            Resource.Error("Network Error", null)
        else if (city.contains("Tokyo"))
            Resource.Success(fakeCurrentWeatherResponses[0])
        else if (city.contains("Sapporo"))
            Resource.Success(fakeCurrentWeatherResponses[1])
        else
            Resource.Error("Error", null)
    }

    override suspend fun getForecastWeatherFromRemote(
        city: String,
        days: Int
    ): Resource<ForecastWeatherResponse> {
        return if (shouldReturnNetworkError)
            Resource.Error("Network Error", null)
        else if (city.contains("Tokyo"))
            Resource.Success(fakeForecastResponses[0])
        else if (city.contains("Sapporo"))
            Resource.Success(fakeForecastResponses[1])
        else
            Resource.Error("Error", null)
    }

    override suspend fun getCurrentWeatherFromCache(city: String): Flow<HourlyWeatherItem?> =
        realmDao.getCityWeatherCurrent(city)

    override suspend fun getWeatherForecastInDaysFromCache(
        city: String,
        days: Int
    ): Flow<List<DailyWeatherItem>> = realmDao.getCityWeatherForecastInDays(city)

    override suspend fun getWeatherForecastInHoursFromCache(
        city: String,
        days: Int
    ): Flow<List<HourlyWeatherItem>> = realmDao.getCityWeatherForecastInHours(city)

    override suspend fun <T> addWeather(city: String, weather: T) =
        realmDao.addWeather(city, weather)

    override suspend fun getCityNameFromRemote(lat: Double, lon: Double): Resource<PlaceResponse> {
        return if (shouldReturnNetworkError || locationMap[Pair(lat, lon)].isNullOrEmpty()) {
            Resource.Error("Error", null)
        } else {
            val country = locationMap[Pair(lat, lon)]?.substringAfter(", ")
            val name = locationMap[Pair(lat, lon)]?.substringBefore(",")
            Resource.Success(arrayListOf(CityData(country, name)) as PlaceResponse)
        }
    }

    override suspend fun searchCity(city: String): Resource<PlaceResponse> {
        TODO("Not yet implemented")
    }
}