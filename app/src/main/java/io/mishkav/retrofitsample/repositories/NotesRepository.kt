package io.mishkav.retrofitsample.repositories

import io.mishkav.retrofitsample.api.NotesApi
import io.mishkav.retrofitsample.entities.Note
import retrofit2.Retrofit

class NotesRepository {

    private val notesApi by lazy { buildApi() }

    fun getNotes(): List<Note> {
    }

    fun createNote() {
    }

    // Hello Dagger
    private companion object {
        const val BASE_URL = "https://quiet-savannah-50383.herokuapp.com/"

        fun buildApi(): NotesApi = buildClient().create(NotesApi::class.java)

        fun buildClient(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
    }
}