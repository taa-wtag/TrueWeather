package com.rektstudios.trueweather.other

import com.rektstudios.trueweather.repositories.UserPrefsRepository
import com.rektstudios.trueweather.repositories.UserPrefsRepository.Companion.KEY_UUID
import java.util.UUID
import javax.inject.Inject

class SessionKey @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository
) {
    private var uuid: String = ""

    suspend fun getUUID(): String{
        if (uuid.isEmpty())
            uuid=fetchUUIDFromPrefs()
        if (uuid.isEmpty())
            createUUID()
        return uuid
    }

    private suspend fun createUUID(){
        uuid = UUID.randomUUID().toString()
        userPrefsRepository.saveValue(KEY_UUID, uuid)
    }

    private suspend fun fetchUUIDFromPrefs():String{
        return userPrefsRepository.readValue(KEY_UUID)
    }
}