package com.example.notetakingdemo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notetakingdemo.notes.Note
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesApp() {
    var notesList by remember { mutableStateOf(listOf(
        Note("Meeting Notes", "Discuss project milestones and deadlines."),
        Note("Shopping List", "Milk, Bread, Eggs, Butter."),
        Note("Workout Plan", "Monday: Cardio, Tuesday: Strength Training."),
        Note("Book Ideas", "Outline chapters and main concepts."),
        Note("Daily Journal", "Write about today's experiences and thoughts.")
    )) }

    var showDialog by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        NotesList(notesList, modifier = Modifier.padding(paddingValues))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Add Note") },
            text = {
                Column {
                    TextField(value = newTitle, onValueChange = { newTitle = it }, label = { Text("Title") })
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(value = newDescription, onValueChange = { newDescription = it }, label = { Text("Description") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (newTitle.isNotBlank() && newDescription.isNotBlank()) {
                        notesList = notesList + Note(newTitle, newDescription)
                        newTitle = ""
                        newDescription = ""
                        showDialog = false
                    }
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun NotesList(notesList: List<Note>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp)) {
        items(notesList) { note ->
            NoteCard(note)
        }
    }
}

@Composable
fun NoteCard(note: Note) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.title, fontSize = 20.sp, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = note.description, fontSize = 16.sp, style = MaterialTheme.typography.bodyMedium)
        }
    }
}