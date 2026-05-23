package appDatabase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


data class NoteItem(
    val id: Int = 0,
    val title: String?,
    val description: String?,
) {
    fun toEntity(): NoteEntity {
        return NoteEntity(
            id = id,
            title = title ?: "",
            description = description ?: ""
        )
    }
}

interface NotesRepository {
    suspend fun insert(note: NoteItem)
    suspend fun update(note: NoteItem)
    suspend fun delete(note: NoteItem)
    suspend fun getById(id: Int): NoteItem?
    suspend fun getAll(): List<NoteItem>
    suspend fun getAllAsFlow(): Flow<List<NoteItem>>
}

class NotesRepositoryImpl(
    private val notesDao: NotesDao
): NotesRepository {
    override suspend fun insert(note: NoteItem) {
        notesDao.insert(note.toEntity())
    }

    override suspend fun update(note: NoteItem) {
        notesDao.update(note.toEntity())
    }

    override suspend fun delete(note: NoteItem) {
        notesDao.deleteById(note.id)
    }

    override suspend fun getById(id: Int): NoteItem? {
        return notesDao.getById(id).let { NoteItem(it.id, it.title, it.description) }
    }

    override suspend fun getAll(): List<NoteItem> {
        return notesDao.getAll().map { NoteItem(it.id, it.title, it.description) }
    }

    override suspend fun getAllAsFlow(): Flow<List<NoteItem>> {
        return notesDao.getAllAsFlow().map { list: List<NoteEntity> -> list.map { NoteItem(it.id, it.title, it.description) } }

    }

}