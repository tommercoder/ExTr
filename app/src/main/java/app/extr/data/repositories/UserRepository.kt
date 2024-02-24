package app.extr.data.repositories

import app.extr.data.daos.UserDao
import app.extr.data.types.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun update(user: User)
    fun getUser(): Flow<User>
}

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun insert(user: User) {
        userDao.insert(user)
    }

    override suspend fun update(user: User) {
        userDao.update(user)
    }

    override fun getUser(): Flow<User> {
        return userDao.getUser()
    }

}