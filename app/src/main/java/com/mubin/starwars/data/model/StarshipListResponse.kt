package com.mubin.starwars.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class StarshipListResponse(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("next")
    var next: String?,
    @SerializedName("previous")
    var previous: Any?,
    @SerializedName("results")
    var results: List<Result?>?
) {
    @Keep
    data class Result(
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
        var pilots: List<String?>?,
        @SerializedName("films")
        var films: List<String?>?,
        @SerializedName("created")
        var created: String?,
        @SerializedName("edited")
        var edited: String?,
        @SerializedName("url")
        var url: String?
    )
}