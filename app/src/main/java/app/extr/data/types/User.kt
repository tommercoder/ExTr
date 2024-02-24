package app.extr.data.types

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "User"
)
