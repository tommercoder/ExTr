package app.extr.data.repositories

import app.extr.data.daos.MoneyTypeDao
import app.extr.data.daos.UserDao
import app.extr.data.types.MoneyType
import app.extr.data.types.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository{
    suspend fun insert(user: User)
    suspend fun delete(id: Int)
    suspend fun selectUser(id: Int)
    fun getSelectedUser(): User?
    fun getAllUsers(): Flow<List<User>>
}

class UsersRepositoryImpl(private val usersDao : UserDao) : UsersRepository  {
    override suspend fun insert(user: User) {
        usersDao.insert(user)
    }

    override suspend fun delete(id: Int) {
        //usersDao.delete(id)
    }

    override suspend fun selectUser(id: Int) {
        usersDao.selectUser(id)
    }

    override fun getSelectedUser(): User? {
        return usersDao.getSelectedUser()
    }

    override fun getAllUsers(): Flow<List<User>> {
        return usersDao.getAllUsers()
    }

}