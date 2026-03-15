package com.rektstudios.trueweather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rektstudios.trueweather.data.local.dao.IDatabaseDao
import com.rektstudios.trueweather.data.local.entity.CityEntity
import com.rektstudios.trueweather.data.local.entity.DailyWeatherEntity
import com.rektstudios.trueweather.data.local.entity.HourlyWeatherEntity

@Database(entities = [CityEntity::class, DailyWeatherEntity::class, HourlyWeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: IDatabaseDao

    companion object {
        const val DATABASE_NAME = "weather_database"
    }
}
