package io.mishkav.retrofitsample.api

import io.mishkav.retrofitsample.entities.Note
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NotesApi {

    @GET("notes")
    suspend fun getNotes() : Response<List<Note>>

    @POST("new-note")
    suspend fun createNote(@Body note: Note)

}