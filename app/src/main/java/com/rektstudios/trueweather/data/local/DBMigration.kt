package com.rektstudios.trueweather.data.local

import io.realm.DynamicRealm
import io.realm.RealmMigration

class DBMigration: RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema
        var existingVersion: Int = oldVersion.toInt()
    }
}