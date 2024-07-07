package appDatabase

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubRepoDao {

    @Insert
    suspend fun insert(item: GithubRepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<GithubRepoEntity>)

    @Query("SELECT * FROM GithubRepoEntity")
    fun getAllAsFlow(): Flow<List<GithubRepoEntity>>


    @Query("SELECT * FROM GithubRepoEntity")
    suspend fun getAll(): List<GithubRepoEntity>

    @Query("SELECT count(*) FROM GithubRepoEntity")
    suspend fun count(): Int

}


@Entity
data class GithubRepoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val stars: String,
    val description: String,
)