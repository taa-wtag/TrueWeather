package com.rektstudios.trueweather.data.repository

import com.rektstudios.trueweather.data.local.CityItem
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.local.WeatherDayItem
import com.rektstudios.trueweather.data.local.WeatherHourItem
import com.rektstudios.trueweather.data.reponse.weather.Condition
import com.rektstudios.trueweather.data.reponse.weather.CurrentWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.Forecast
import com.rektstudios.trueweather.data.reponse.weather.ForecastWeatherResponse
import com.rektstudios.trueweather.data.reponse.weather.Forecastday
import com.rektstudios.trueweather.data.reponse.weather.Place
import com.rektstudios.trueweather.data.reponse.weather.PlaceResponse
import com.rektstudios.trueweather.data.reponse.weather.WeatherDay
import com.rektstudios.trueweather.data.reponse.weather.WeatherHour
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class FakeWeatherRepository(private val realmDao: IRealmDao):IWeatherRepository {
    private val locationMap = mutableMapOf(
        Pair(Pair(23.72, 90.41),"Dhaka, Bangladesh"),
        Pair(Pair(26.68, 85.17),"Dhaka, India"),
        Pair(Pair(34.05, -118.24),"Los Angeles, United States"),
        Pair(Pair(13.75, 100.52),"Bangkok, Thailand"),
        Pair(Pair(35.69, 139.69),"Tokyo, Japan"),
    )

    val fakeCurrentWeatherResponses = listOf(CurrentWeatherResponse(
        WeatherHour(
            Condition("//cdn.weatherapi.com/weather/64x64/day/296.png","Light rain"),
            21.0,
            69.8,
            78,
            1,
            21.0,
            69.8,
            "2024-06-02 14:45",
            1717307488,
            8,
            4,
            9.0,
            5.6
        )
    ),
        CurrentWeatherResponse(
            WeatherHour(
                Condition("//cdn.weatherapi.com/weather/64x64/day/116.png","Partly cloudy"),
                18.0,
                64.4,
                56,
                1,
                18.0,
                64.4,
                "2024-06-02 14:45",
                1717307488,
                10,
                6,
                24.1,
                15.0
            )
        )
    )
    val fakeForecastResponses = listOf(
        ForecastWeatherResponse(
            WeatherHour(
                Condition("//cdn.weatherapi.com/weather/64x64/day/296.png","Light rain"),
                21.0,
                69.8,
                78,
                1,
                21.0,
                69.8,
                "2024-06-02 14:45",
                1717307488,
                8,
                4,
                9.0,
                5.6
            ),
            Forecast(
                listOf(
                    Forecastday(
                        "2024-06-02",
                        1717286400,
                        WeatherDay(
                            71,
                            19.4,
                            66.9,
                            8.3,
                            5.0,
                            Condition(
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
                            WeatherHour(
                                Condition(
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
                                2,
                                1,
                                22.0,
                                13.6
                            )
                        )
                    )
                )
            )
        ),
        ForecastWeatherResponse(
            WeatherHour(
                Condition("//cdn.weatherapi.com/weather/64x64/day/116.png","Partly cloudy"),
                18.0,
                64.4,
                56,
                1,
                18.0,
                64.4,
                "2024-06-02 14:45",
                1717307488,
                10,
                6,
                24.1,
                15.0
            ),
            Forecast(
                listOf(
                    Forecastday(
                        "2024-06-02",
                        1717286400,
                        WeatherDay(
                            65,
                            12.1,
                            53.8,
                            10.0,
                            6.0,
                            Condition(
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
                            WeatherHour(
                                Condition(
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
                                10,
                                6,
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

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }
    override suspend fun getCurrentWeatherFromRemote(city: String): Resource<CurrentWeatherResponse> {
        return if(shouldReturnNetworkError)
            Resource.Error("Network Error", null)
        else if(city.contains("Tokyo"))
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
        return if(shouldReturnNetworkError)
            Resource.Error("Network Error", null)
        else if(city.contains("Tokyo"))
            Resource.Success(fakeForecastResponses[0])
        else if (city.contains("Sapporo"))
            Resource.Success(fakeForecastResponses[1])
        else
            Resource.Error("Error", null)
    }

    override suspend fun getCurrentWeatherFromCache(cityItem: CityItem): Flow<WeatherHourItem?>
    = realmDao.getCityWeatherCurrent(cityItem)

    override suspend fun getWeatherForecastInDaysFromCache(
        cityItem: CityItem,
        days: Int
    ): Flow<WeatherDayItem>? = realmDao.getCityWeatherForecastInDays(cityItem)

    override suspend fun getWeatherForecastInHoursFromCache(
        cityItem: CityItem,
        days: Int
    ): Flow<WeatherHourItem>? = realmDao.getCityWeatherForecastInHours(cityItem)

    override suspend fun <T> addWeather(cityItem: CityItem, weather: T) = realmDao.addWeather(cityItem,weather)

    override suspend fun getCityNameFromRemote(lat: Double, lon: Double): Resource<PlaceResponse> {
        return if(shouldReturnNetworkError || locationMap[Pair(lat,lon)].isNullOrEmpty()){
            Resource.Error("Error", null)
        }
        else{
            val country = locationMap[Pair(lat,lon)]?.substringAfter(", ")
            val name = locationMap[Pair(lat,lon)]?.substringBefore(",")
            Resource.Success(
                    arrayListOf(Place(
                        country,
                        name
                    )) as PlaceResponse
            )
        }
    }

    override suspend fun searchCity(city: String): Resource<PlaceResponse> {
        TODO("Not yet implemented")
    }
}