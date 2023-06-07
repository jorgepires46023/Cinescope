package pt.isel.ps.cinescope.repositories.jdbi


import org.jdbi.v3.core.Handle
import pt.isel.ps.cinescope.domain.User
import pt.isel.ps.cinescope.repositories.UsersRepository
import pt.isel.ps.cinescope.services.exceptions.InternalServerErrorException
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

    override fun removeUser(id: Int) {
        handle.createUpdate("update cinescope.users set state = :state where userid = :userid")
            .bind("state", "Inactive")
            .bind("userid", id)
            .execute()
    }


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

    override fun updateUserInfo(user: User) {
        handle.createUpdate("update cinescope.users set name = :name, email = :email, password = :password " +
                "where userid = :userid")
            .bind("name", user.name)
            .bind("email", user.email)
            .bind("password", user.password)
            .bind("userid", user.id)
            .execute()
    }

    override fun getUserByToken(token: String): User? =
        handle.createQuery("select userid as id, name, email, token, password, state from cinescope.users " +
                "where token = :token")
            .bind("token", token)
            .mapTo(User::class.java)
            .singleOrNull()
}