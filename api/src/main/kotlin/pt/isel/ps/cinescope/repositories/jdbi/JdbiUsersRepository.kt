package pt.isel.ps.cinescope.repositories.jdbi

import org.springframework.asm.Handle
import pt.isel.ps.cinescope.domain.User
import pt.isel.ps.cinescope.repositories.UsersRepository
import java.util.*

class JdbiUsersRepository (private val handle: Handle): UsersRepository {
    override fun getUserById(id: Int): User? {
        TODO("Not yet implemented")
    }

    override fun insertUser(user: User): Int? {
        TODO("Not yet implemented")
    }

    override fun removeUser(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getUserByEmail(email: String): User? {
        TODO("Not yet implemented")
    }

    override fun getUserIdByToken(token: UUID): Int? {
        TODO("Not yet implemented")
    }

    override fun getUserByToken(token: String): User? {
        TODO("Not yet implemented")
    }
}