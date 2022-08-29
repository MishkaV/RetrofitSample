package io.mishkav.retrofitsample.entities

import com.google.gson.annotations.SerializedName

data class Note(
    @SerializedName("timespamp")
    val timestamp: String,
    val title: String,
    val content: String
)
