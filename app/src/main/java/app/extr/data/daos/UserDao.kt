package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.extr.data.types.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    //@Delete
    //suspend fun delete(id: Int)

    @Update
    suspend fun update(user: User)

    @Query("UPDATE users SET lastSelected = CASE WHEN id = :id THEN 1 ELSE 0 END")
    suspend fun selectUser(id: Int)

    @Query("SELECT * FROM users WHERE lastSelected = 1")
    fun getSelectedUser(): User?

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>
}