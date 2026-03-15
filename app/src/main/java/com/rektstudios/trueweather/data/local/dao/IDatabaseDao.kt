package com.rektstudios.trueweather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rektstudios.trueweather.data.local.entity.CityEntity
import com.rektstudios.trueweather.data.local.entity.DailyWeatherEntity
import com.rektstudios.trueweather.data.local.entity.HourlyWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(city: CityEntity)

    @Query("DELETE FROM city_table WHERE cityName = :city")
    suspend fun deleteCity(city: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeather(weather: DailyWeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeather(weather: HourlyWeatherEntity)

    @Transaction
    @Query("SELECT * FROM city_table")
    fun getCityList(): Flow<List<CityEntity>>

    @Query("SELECT * FROM city_table WHERE cityName = :city")
    suspend fun getCity(city: String): CityEntity?

    @Query(
        """
        SELECT h.* FROM hourly_weather_table h
        WHERE h.cityName = :city
        ORDER BY h.timeEpoch DESC
        LIMIT 1
    """,
    )
    fun getCityWeatherCurrent(city: String): Flow<HourlyWeatherEntity?>

    @Query(
        """
        SELECT h.* FROM daily_weather_table h
        WHERE h.cityName = :city
    """,
    )
    fun getCityWeatherForecastInDays(city: String): Flow<List<DailyWeatherEntity>>

    @Query(
        """
        SELECT h.* FROM hourly_weather_table h
        WHERE h.cityName = :city
    """,
    )
    fun getCityWeatherForecastInHours(city: String): Flow<List<HourlyWeatherEntity>>
}
