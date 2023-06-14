package pt.isel.ps.cinescope.controllers

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ps.cinescope.controllers.models.LoginInputModel
import pt.isel.ps.cinescope.controllers.models.UserInputModel
import pt.isel.ps.cinescope.services.UsersServices
import java.util.Date

@RestController
class UsersController(val usersService: UsersServices) {

    @PostMapping(Users.CREATE_USER)
    fun createUser(@RequestBody info: UserInputModel): ResponseEntity<*> {
        val user = usersService.createUser(info.name, info.email, info.password)

        return ResponseEntity
                .status(201)
                .body(user)
    }

    @PutMapping(Users.DELETE_USER)
    fun removeUser(@PathVariable id: Int): ResponseEntity<*> {
        val user = usersService.removeUser(id)

        return ResponseEntity
            .status(200)
            .body(user)
    }

    /*@PostMapping(Users.UPDATE_USER)
    fun editUser(@RequestBody info: UserInputModel, @PathVariable id: Int): ResponseEntity<*> {
        val user = usersService.editUser(id, info.name, info.email, info.password)

        return ResponseEntity
            .status(200)
            .body(user)
    }*/

    @GetMapping(Users.GET_USER_INFO)
    fun getUserInfo(@PathVariable id: Int): ResponseEntity<*> {
        val user = usersService.getUserById(id)

        return ResponseEntity
            .status(200)
            .body(user)
    }

    @PostMapping(Users.LOGIN)
    fun login(@RequestBody info: LoginInputModel): ResponseEntity<*>{
        val user = usersService.login(info.email, info.password)
        val cookie = HttpHeaders()
        val time = 30*24*60*60 //1 month
        cookie.add(HttpHeaders.SET_COOKIE, "userToken=${user.token}; Max-Age=$time")

        return ResponseEntity
            .status(200)
            .headers(cookie)
            .body(user)
    }

}