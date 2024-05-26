package com.rektstudios.trueweather.data.remote.reponses.mapbox

import com.google.gson.annotations.SerializedName

data class Suggestion(
    val address: String?,
    val brand: List<String>?,
    val context: Context?,
    val distance: Int?,
    @SerializedName("external_ids")
    val externalIds: ExternalIds?,
    @SerializedName("feature_type")
    val featureType: String?,
    @SerializedName("full_address")
    val fullAddress: String?,
    val language: String?,
    val maki: String?,
    @SerializedName("mapbox_id")
    val mapboxId: String?,
    val metadata: Metadata?,
    val name: String?,
    @SerializedName("place_formatted")
    val placeFormatted: String?,
    @SerializedName("poi_category")
    val poiCategory: List<String>?,
    @SerializedName("poi_category_ids")
    val poiCategoryIds: List<String>?
)