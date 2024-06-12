package com.rektstudios.trueweather.data.local

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId


open class CityItem: RealmObject() {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var cityName: String = ""
    var formattedName: String = ""
    var country: String = ""
    var countryCode: String = ""
    var language: String = ""
    var dateItems: RealmList<DateItem> = RealmList()
}