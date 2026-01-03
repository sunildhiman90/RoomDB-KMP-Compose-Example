import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import appDatabase.DBFactory
import appDatabase.NoteItem
import appDatabase.NotesRepository
import appDatabase.NotesRepositoryImpl
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview


//Added some UI to save time
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    dbFactory: DBFactory
) {
    MaterialTheme {


        val appDatabase = remember {  dbFactory.createDatabase() }
        val notesRepository = remember { NotesRepositoryImpl(appDatabase.notesDao()) }


        val scope = rememberCoroutineScope()


        var updated by remember { mutableStateOf(false) }


        var notes = remember { mutableStateListOf<NoteItem>() }


        LaunchedEffect(updated) {
            scope.launch {
                notesRepository.getAll().let {
                    //Fetch from db
                    notes.clear()
                    notes.addAll(it)
                }
            }
        }


        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Room Db Notes App", color = MaterialTheme.colorScheme.onPrimary)
                    }, actions = {})
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                NoteInput { newItem ->
                    scope.launch {
                        //insert in db
                        notesRepository.insert(NoteItem(title = newItem, description = "${newItem} Desc"))
                        updated = !updated
                    }
                }
                NoteItems(notes, onDelete = { item ->
                    scope.launch {
                       //delete in db
                        notesRepository.delete(item)
                        updated = !updated
                    }
                })
            }
        }
    }
}


@Composable
private fun NoteInput(onAdd: (String) -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        var text by remember { mutableStateOf("") }
        OutlinedTextField(
            value = text,
            modifier = Modifier.weight(1f),
            onValueChange = {
                text = it
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = {
            if (text.isNotBlank()) {
                onAdd(text)
                text = ""
            }
        }) {
            Text("Add")
        }
    }
}


@Composable
private fun NoteItems(notes: List<NoteItem>, onDelete: (NoteItem) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(notes, key = {
            it.id
        }) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = it.title ?: "",
                        modifier = Modifier.weight(1f),
                    )
                    Button(
                        onClick = {
                            onDelete(it)
                        }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
