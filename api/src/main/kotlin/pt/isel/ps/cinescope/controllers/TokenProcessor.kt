package pt.isel.ps.cinescope.controllers

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.User
import pt.isel.ps.cinescope.services.UsersServices
import pt.isel.ps.cinescope.services.exceptions.BadRequestException

@Component
class TokenProcessor(private val usersService: UsersServices) {

    fun processToken(token: String?): User? {
        if (token.isNullOrBlank()) {
            throw BadRequestException("Token cannot be null or blank")
        }
//        val split = token.trim().split(" ")
//        if (split.size != 2) {
//            return null
//        }
//        if (split[0].lowercase() != SCHEME) {
//            return null
//        }
        return usersService.getUserByToken(token)
    }

    companion object {
        const val SCHEME = "bearer"
    }
}