package com.rektstudios.trueweather.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "city_table", indices = [Index(value = ["cityName"], unique = true)])
data class CityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var cityName: String,
    var backgroundColor: Int? = null,
)
