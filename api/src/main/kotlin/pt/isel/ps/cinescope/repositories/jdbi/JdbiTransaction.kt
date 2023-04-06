package pt.isel.ps.cinescope.repositories.jdbi


import org.jdbi.v3.core.Handle
import pt.isel.ps.cinescope.repositories.Transaction
import pt.isel.ps.cinescope.repositories.UsersRepository

class JdbiTransaction(
    private val handle: Handle
): Transaction {
    override val userRepository: UsersRepository by lazy {JdbiUsersRepository(handle)}

    override fun rollback() {
        handle.rollback()
    }
}