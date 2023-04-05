package pt.isel.ps.cinescope.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.ps.cinescope.controllers.models.LoginInputModel
import pt.isel.ps.cinescope.controllers.models.UpdateUserInputModel
import pt.isel.ps.cinescope.controllers.models.UserIdInputModel
import pt.isel.ps.cinescope.controllers.models.UserInputModel
import pt.isel.ps.cinescope.services.UsersServices

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
    fun removeUser(@RequestBody info: UserIdInputModel): ResponseEntity<*> {
        val user = usersService.removeUser(info.userId)

        return ResponseEntity
            .status(200)
            .body(user)
    }

    @PutMapping(Users.UPDATE_USER)
    fun editUser(@RequestBody info: UpdateUserInputModel): ResponseEntity<*> {
        val user = usersService.editUser(info.userId, info.name, info.email, info.password)

        return ResponseEntity
            .status(200)
            .body(user)
    }

    @GetMapping(Users.GET_USER_INFO)
    fun getUserInfo(@RequestBody info: UserIdInputModel): ResponseEntity<*> {
        val user = usersService.getUserById(info.userId)

        return ResponseEntity
            .status(200)
            .body(user)
    }

    @PostMapping(Users.LOGIN)
    fun login(@RequestBody info: LoginInputModel): ResponseEntity<*>{
        val user = usersService.login(info.email, info.password)

        return ResponseEntity
            .status(200)
            .body(user)
    }

    @GetMapping(Users.GET_USER_LISTS)
    fun getUserLists(@RequestBody info: UserIdInputModel): ResponseEntity<*> {
        val user = "Waiting Services"//TODO usersService.getUserLists(info.id)

        return ResponseEntity
            .status(200)
            .body(user)
    }
}