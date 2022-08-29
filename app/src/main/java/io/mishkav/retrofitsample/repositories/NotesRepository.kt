package io.mishkav.retrofitsample.repositories

import io.mishkav.retrofitsample.api.NotesApi
import io.mishkav.retrofitsample.entities.Note
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotesRepository {

    private val notesApi by lazy { buildApi() }

    suspend fun getNotes() = notesApi.getNotes()

    suspend fun createNote(note: Note) = notesApi.createNote(note)

    // Hello Dagger
    private companion object {
        const val BASE_URL = "https://quiet-savannah-50383.herokuapp.com/"

        fun buildApi(): NotesApi = buildClient().create(NotesApi::class.java)

        fun buildClient(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}