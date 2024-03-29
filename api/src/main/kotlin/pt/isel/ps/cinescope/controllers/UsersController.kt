package pt.isel.ps.cinescope.controllers

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ps.cinescope.controllers.models.LoginInputModel
import pt.isel.ps.cinescope.controllers.models.UserInputModel
import pt.isel.ps.cinescope.controllers.models.UserOutputModel
import pt.isel.ps.cinescope.domain.User
import pt.isel.ps.cinescope.services.UsersServices
import java.util.Date

@RestController
class UsersController(val usersService: UsersServices) {

    @PostMapping(Users.CREATE_USER)
    fun createUser(@RequestBody info: UserInputModel): ResponseEntity<*> {
        val user = usersService.createUser(name = info.name, email = info.email, password = info.password)
        val cookie = createCookie(user)
        return ResponseEntity
            .status(201)
            .headers(cookie)
            .body(UserOutputModel(user.name, user.email))
    }

    @GetMapping(Users.GET_USER_INFO)
    fun getUserInfo(@CookieValue(value = "userToken") token: String): ResponseEntity<*> {
        val user = usersService.getUserByToken(token)
        return ResponseEntity
            .status(200)
            .body(UserOutputModel(user?.name, user?.email))
    }

    @PostMapping(Users.LOGIN)
    fun login(@RequestBody info: LoginInputModel): ResponseEntity<*>{
        val user = usersService.login(info.email, info.password)
        val cookie = createCookie(user)
        return ResponseEntity
            .status(200)
            .headers(cookie)
            .body(UserOutputModel(user.name, user.email))
    }

    fun createCookie(user: User): HttpHeaders{
        val cookie = HttpHeaders()
        val time = 30*24*60*60 //1 month
        cookie.add(HttpHeaders.SET_COOKIE, "userToken=${user.token}; Max-Age=$time; path=/")
        return cookie
    }
}