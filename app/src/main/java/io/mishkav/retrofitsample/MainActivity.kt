package io.mishkav.retrofitsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.mishkav.retrofitsample.entities.Note
import io.mishkav.retrofitsample.repositories.NotesRepository
import io.mishkav.retrofitsample.ui.theme.RetrofitSampleTheme
import io.mishkav.retrofitsample.viewModels.NoteViewModel
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RetrofitSampleTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    NotesScreen()
                }
            }
        }
    }
}

@Composable
fun NotesScreen() = Column(
    modifier = Modifier.fillMaxSize()
) {
    val viewModel: NoteViewModel = viewModel()
    val notes by viewModel.notes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onOpen()
    }

    NotesScreenContent(
        notes = notes,
        onNewNoteCreate = viewModel::setNewNote
    )
}

@Composable
fun NotesScreenContent(
    notes: List<Note>,
    onNewNoteCreate: (title: String, content: String) -> Unit
) = Box(
    modifier = Modifier.fillMaxSize()
) {
    var isCreateNewNoteVisible by remember { mutableStateOf(false) }

    Column {
        notes.forEach {
            Spacer(modifier = Modifier.height(8.dp))

            NoteCard(
                note = it
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = { isCreateNewNoteVisible = true },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                text = "CLICK MEEE!!!"
            )
        }
    }

    if (isCreateNewNoteVisible) {
        CreateNoteScreen(
            onNewNoteCreate = { title, content ->
                onNewNoteCreate(title, content)
                isCreateNewNoteVisible = false
            }
        )
    }

}

@Composable
fun NoteCard(
    note: Note
) = Card(
    modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
    border = BorderStroke(1.dp, Color.Black)
) {
    val space = 2.dp

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(space))

        Text(
            text = note.title,
            style = MaterialTheme.typography.h4
        )

        Spacer(modifier = Modifier.height(space))

        Text(
            text = "timestamp: ${note.timestamp}",
            style = MaterialTheme.typography.subtitle2
        )

        Spacer(modifier = Modifier.height(space * 4))

        Text(
            text = note.content,
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(space))
    }
}

@Composable
fun CreateNoteScreen(
    onNewNoteCreate: (title: String, content: String) -> Unit
) = Column(
    modifier = Modifier
        .padding(8.dp)
        .background(Color.White)
        .fillMaxSize()
) {
    val modifier = Modifier.fillMaxWidth().padding(8.dp)
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    TextField(
        value = title,
        onValueChange = { title = it },
        label = { Text("Title") },
        modifier = modifier
    )

    Spacer(modifier = Modifier.height(4.dp))

    TextField(
        value = content,
        onValueChange = { content = it },
        label = { Text("Content") },
        modifier = modifier
    )

    Button(
        onClick = { onNewNoteCreate(title, content) },
        modifier = modifier
    ) {
        Text(
            text = "CLICK TO SAVE",
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitSampleTheme {
        NoteCard(
            note = Note(
                timestamp = "123123",
                title = "Title",
                content = "Content"
            )
        )
    }
}