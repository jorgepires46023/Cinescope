package pt.isel.ps.cinescope.controllers

import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.domain.User
import pt.isel.ps.cinescope.services.UsersServices

@Component
class TokenProcessor(private val usersService: UsersServices) {

    fun processToken(token: String?): User? {
        if (token == null) {
            return null
        }
        val split = token.trim().split(" ")
        if (split.size != 2) {
            return null
        }
        if (split[0].lowercase() != SCHEME) {
            return null
        }
        return usersService.getUserByToken(split[1])
    }

    companion object {
        const val SCHEME = "bearer"
    }
}