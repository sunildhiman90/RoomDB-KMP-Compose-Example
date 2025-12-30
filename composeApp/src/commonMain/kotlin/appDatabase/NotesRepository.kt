package appDatabase

import kotlinx.coroutines.flow.Flow


data class NoteItem(
    val id: Int = 0,
    val title: String?,
    val description: String?,
)

interface NotesRepository {
    suspend fun insert(note: NoteItem)
    suspend fun update(note: NoteItem)
    suspend fun delete(note: NoteItem)
    suspend fun getById(id: Int): NoteItem?
    suspend fun getAll(): List<NoteItem>
    fun getAllAsFlow(): Flow<List<NoteItem>>
}
