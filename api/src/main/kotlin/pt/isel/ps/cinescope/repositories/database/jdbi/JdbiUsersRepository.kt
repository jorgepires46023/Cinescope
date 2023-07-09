package pt.isel.ps.cinescope.repositories.database.jdbi


import org.jdbi.v3.core.Handle
import pt.isel.ps.cinescope.domain.User
import pt.isel.ps.cinescope.repositories.database.UsersRepository
import java.util.*

class  JdbiUsersRepository (private val handle: Handle): UsersRepository {
    override fun getUserById(id: Int): User? =
        handle.createQuery("select userid as id, name, email, token, password, state from cinescope.users where userid = :id")
            .bind("id", id)
            .mapTo(User::class.java)
            .singleOrNull()

    override fun insertUser(user: User): Int? =
        handle.createUpdate("insert into cinescope.users(userid, token, name, email, password, state) " +
                        "values (DEFAULT, DEFAULT, :name, :email, :password, :state)")
                .bind("name", user.name)
                .bind("email", user.email)
                .bind("password", user.password)
                .bind("state", user.state)
                .executeAndReturnGeneratedKeys()
                .mapTo(Int::class.java)
                .firstOrNull()

    override fun getUserByEmail(email: String): User? =
        handle.createQuery("select userid as id, token, name, email, password from cinescope.users " +
                "where email = :email AND state = 'Active'")
            .bind("email", email)
            .mapTo(User::class.java)
            .singleOrNull()

    override fun getUserIdByToken(token: UUID): Int? =
        handle.createQuery("select userid as id, name from cinescope.users " +
                "where token = :token")
            .bind("token", token)
            .mapTo(Int::class.java)
            .singleOrNull()

    override fun getUserByToken(token: String): User? =
        handle.createQuery("select userid as id, name, email, token, password, state from cinescope.users " +
                "where token = :token")
            .bind("token", token)
            .mapTo(User::class.java)
            .singleOrNull()
}