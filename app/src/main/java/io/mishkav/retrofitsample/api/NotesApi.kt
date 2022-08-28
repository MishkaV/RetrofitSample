package io.mishkav.retrofitsample.api

import retrofit2.http.GET

interface NotesApi {

    @GET("notes")
    fun getNotes()

}