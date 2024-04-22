package com.mubin.starwars.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CharacterDetailsResponse(
    @SerializedName("name")
    var name: String?,
    @SerializedName("height")
    var height: String?,
    @SerializedName("mass")
    var mass: String?,
    @SerializedName("hair_color")
    var hairColor: String?,
    @SerializedName("skin_color")
    var skinColor: String?,
    @SerializedName("eye_color")
    var eyeColor: String?,
    @SerializedName("birth_year")
    var birthYear: String?,
    @SerializedName("gender")
    var gender: String?,
    @SerializedName("homeworld")
    var homeworld: String?,
    @SerializedName("films")
    var films: List<String?>?,
    @SerializedName("species")
    var species: List<Any?>?,
    @SerializedName("vehicles")
    var vehicles: List<String?>?,
    @SerializedName("starships")
    var starships: List<String?>?,
    @SerializedName("created")
    var created: String?,
    @SerializedName("edited")
    var edited: String?,
    @SerializedName("url")
    var url: String?
)