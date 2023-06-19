package pt.isel.ps.cinescope.services

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.controllers.TokenProcessor
import pt.isel.ps.cinescope.domain.User
import pt.isel.ps.cinescope.domain.UserState
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.services.exceptions.BadRequestException
import pt.isel.ps.cinescope.services.exceptions.InternalServerErrorException
import pt.isel.ps.cinescope.services.exceptions.NotFoundException
import pt.isel.ps.cinescope.services.exceptions.UnauthorizedException
import pt.isel.ps.cinescope.utils.Encoder
import pt.isel.ps.cinescope.utils.isNull
import java.util.*

@Component
class UsersServices(val passwordEncoder: Encoder, private val transactionManager: TransactionManager, /*private val tokenProcessor: TokenProcessor*/) {

    fun getUserById(id: Int?): User {
        if (id == null) throw BadRequestException("Id cannot be null")
        return transactionManager.run { it.userRepository.getUserById(id)?: throw NotFoundException("User not Found") }
    }

    fun createUser(name: String?, email: String?, password: String?): User {
        if(name.isNullOrBlank() || email.isNullOrBlank()) throw BadRequestException("Name Or Email not provided")
        if(password.isNullOrBlank())throw BadRequestException("Password not provided or not valid")

        val encodedPassword = passwordEncoder.encodeInfo(password)

        val user = User(null, name, email, encodedPassword, UUID.randomUUID(), UserState.Active)

        val id =  transactionManager.run {
            return@run it.userRepository.insertUser(user) ?: throw InternalServerErrorException("Couldnt create user")
        }
        return getUserById(id)
    }

    fun removeUser(id :Int?) {
        if (id == null) throw BadRequestException("Id cannot be null")
        transactionManager.run { it.userRepository.removeUser(id) }
    }

  /*  fun editUser(bearer: String?, name: String?, email: String?,password: String?) {
        if(name.isNullOrBlank() || email.isNullOrBlank() || password.isNullOrBlank()) {
            throw BadRequestException("Info to edit cannot be empty")
        }

        if(bearer.isNullOrBlank()) throw BadRequestException("Token cannot be null or blank")
        val user = tokenProcessor.processToken(bearer) ?: throw NotFoundException("User not found")

        transactionManager.run {
            val encodedPassword = passwordEncoder.encodeInfo(password)
            val updatedUser = User(user.id, name, email, encodedPassword, user.token, user.state)
            it.userRepository.updateUserInfo(updatedUser)
        }
    }
*/

    fun login(email: String?, password: String?): User {
        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            throw BadRequestException("You must provide valid email or password")
        }
        return transactionManager.run{

            val user = it.userRepository.getUserByEmail(email) ?: throw NotFoundException("User doesn't exist")

            if(passwordEncoder.validateInfo(password, user.password)) {
                return@run user
            }
            throw UnauthorizedException("Unauthorized")
        }
    }

    fun getUserByToken(usertoken: String?): User?{
        if(usertoken.isNullOrBlank()) throw BadRequestException("token cant be null")
        return transactionManager.run{
            val user = it.userRepository.getUserByToken(usertoken) ?:throw NotFoundException("user not found")
            return@run user
        }
    }

}
