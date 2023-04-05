package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.User
import pt.isel.ps.cinescope.domain.UserState
import pt.isel.ps.cinescope.domain.validatePassword
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.services.exceptions.UnauthorizedException
import pt.isel.ps.cinescope.utils.Encoder
import java.util.*

@Component
class UsersServices(val passwordEncoder: Encoder, private val transactionManager: TransactionManager) {

    fun getUserById(id: Int?): User? {
        if (id == null) throw BadRequestException("Id cannot be null")
        return transactionManager.run { it.userRepository.getUserById(id) }
    }

    fun createUser(name: String?, email: String?, password: String?): Int? {

        if(name.isNullOrBlank() || email.isNullOrBlank()) {
            throw BadRequestException("Name Or Email not provided")
        }

        if(password.isNullOrBlank()){
            throw BadRequestException("Password not provided or not valid")
        }

        val encodedPassword = passwordEncoder.encodeInfo(password)

        val user = User(null, name, email, encodedPassword, UUID.randomUUID(), UserState.Active)

        return transactionManager.run { it.userRepository.insertUser(user) }

    }

    fun removeUser(id :Int?) {
        if (id == null) throw BadRequestException("Id cannot be null")
        transactionManager.run { it.userRepository.removeUser(id) }
    }

    fun editUser(id: Int?, name: String?, email: String?,password: String?) {

        if(name.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank() || id == null) {
            throw BadRequestException("Info to edit cannot be empty")
        }

        transactionManager.run {
            val user = it.userRepository.getUserById(id)?: throw NotFoundException("User not found")
            val encodedPassword = passwordEncoder.encodeInfo(password)
            val updatedUser = User(user.id, name, email, encodedPassword, user.token, user.state)
            it.userRepository.updateUserInfo(updatedUser)
        }
    }

    fun getUserByToken(token: UUID?): User? {
        if(token == null) throw BadRequestException("Id cannot be null")

        return transactionManager.run { it.userRepository.getUserByToken(token.toString()) }
    }

    fun login(email: String?, password: String?): User? {
        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            throw BadRequestException("You must provide valid email or password")
        }
        return transactionManager.run{

            val user = it.userRepository.getUserByEmail(email) ?: throw NotFoundException("User doesn't exist")

            if(validatePassword(user.password, password)) {
                return@run user
            }
            throw UnauthorizedException("Unauthorized")
        }
    }

}
