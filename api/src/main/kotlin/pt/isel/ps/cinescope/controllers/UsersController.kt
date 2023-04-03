package pt.isel.ps.cinescope.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
    fun deleteUser(@RequestBody info: UserIdInputModel): ResponseEntity<*> {
        val user = "Waiting Services" //TODO usersService.deleteUser(info.id)

        return ResponseEntity
            .status(201)
            .body(user)
    }

    @GetMapping(Users.GET_USER_INFO)
    fun getUserInfo(@RequestBody info: UserIdInputModel): ResponseEntity<*> {
        val user = "Waiting Services"//TODO usersService.getUserInfo(info.id)

        return ResponseEntity
            .status(201)
            .body(user)
    }

    @GetMapping(Users.GET_USER_LISTS)
    fun getUserLists(@RequestBody info: UserIdInputModel): ResponseEntity<*> {
        val user = "Waiting Services"//TODO usersService.getUserLists(info.id)

        return ResponseEntity
            .status(201)
            .body(user)
    }
}