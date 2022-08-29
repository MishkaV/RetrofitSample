package io.mishkav.retrofitsample.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.mishkav.retrofitsample.entities.Note
import io.mishkav.retrofitsample.repositories.NotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NoteViewModel : ViewModel() {

    private val notesRepository by lazy { NotesRepository() }

    val notes = MutableStateFlow<List<Note>>(listOf())

    fun onOpen() {
        getNotes()
    }


    fun getNotes() = viewModelScope.launch {
        notesRepository.getNotes().body()?.let {
            Log.d(TAG, "getNotes() - list: $it")
            notes.value = it
        }
    }

    fun setNewNote(title: String, content: String) = viewModelScope.launch {
        notesRepository.createNote(
            note = Note(
                timestamp = System.currentTimeMillis().toString(),
                title = title,
                content = content
            )
        )
        getNotes()
    }

    private companion object {
        const val TAG = "TAG_CHECK"
    }
}