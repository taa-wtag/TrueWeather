package com.rektstudios.trueweather.data.reponse.mapbox

import kotlinx.serialization.Serializable

@Serializable
data class GeoJsonResponse(
    val features: List<Feature>,
)
