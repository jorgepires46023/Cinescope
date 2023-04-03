package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.User
import pt.isel.ps.cinescope.domain.UserState
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.utils.Encoder
import java.util.*

@Component
class UsersServices(val passwordEncoder: Encoder) {

    fun createUser(name: String?, email: String?, password: String): User { //TODO("Mais tarde não devolver user")

        if(name.isNullOrBlank() || email.isNullOrBlank()) {
            throw BadRequestException("Name Or Email not provided")
        }

        val encodedPassword = passwordEncoder.encodeInfo(password)

        return User(name, email, encodedPassword, UUID.randomUUID(), UserState.Active)
    }

    fun deleteUser(id :Int?) {

    }

    fun editUser(name: String?, email: String?,password: String?) {

        if(name.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank()) {
            throw BadRequestException("Info to edit cannot be empty")
        }

    }

    fun login() {

    }



}
