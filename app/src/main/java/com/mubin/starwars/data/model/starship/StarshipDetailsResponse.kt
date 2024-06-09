package com.mubin.starwars.data.model.starship


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StarshipDetailsResponse(
    @SerializedName("name")
    var name: String?,
    @SerializedName("model")
    var model: String?,
    @SerializedName("manufacturer")
    var manufacturer: String?,
    @SerializedName("cost_in_credits")
    var costInCredits: String?,
    @SerializedName("length")
    var length: String?,
    @SerializedName("max_atmosphering_speed")
    var maxAtmospheringSpeed: String?,
    @SerializedName("crew")
    var crew: String?,
    @SerializedName("passengers")
    var passengers: String?,
    @SerializedName("cargo_capacity")
    var cargoCapacity: String?,
    @SerializedName("consumables")
    var consumables: String?,
    @SerializedName("hyperdrive_rating")
    var hyperdriveRating: String?,
    @SerializedName("MGLT")
    var mGLT: String?,
    @SerializedName("starship_class")
    var starshipClass: String?,
    @SerializedName("pilots")
    var pilots: List<Any?>?,
    @SerializedName("films")
    var films: List<String?>?,
    @SerializedName("created")
    var created: String?,
    @SerializedName("edited")
    var edited: String?,
    @SerializedName("url")
    var url: String?
)