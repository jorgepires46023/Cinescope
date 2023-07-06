package pt.isel.ps.cinescope.repositories.database

import pt.isel.ps.cinescope.domain.User
import java.util.*

interface UsersRepository {
    fun getUserById(id: Int): User?

    fun insertUser(user: User): Int?

    fun removeUser(id: Int)
    fun getUserByEmail(email: String): User?
    fun getUserIdByToken(token: UUID): Int?
    fun updateUserInfo(user: User)

    fun getUserByToken(token: String): User?
}