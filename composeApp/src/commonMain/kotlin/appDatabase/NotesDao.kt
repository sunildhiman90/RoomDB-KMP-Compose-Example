package appDatabase

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String
)


@Dao
interface NotesDao {

    @Insert
    suspend fun insert(item: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<NoteEntity>)

    @Update
    suspend fun update(item: NoteEntity)

    @Upsert
    suspend fun upsert(item: NoteEntity)

    @Query("SELECT * FROM NoteEntity")
    suspend fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM NoteEntity")
    fun getAllAsFlow(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NoteEntity WHERE id = :id")
    suspend fun getById(id: Int): NoteEntity

    @Query("DELETE FROM NoteEntity WHERE id = :id")
    suspend fun deleteById(id: Int)


}