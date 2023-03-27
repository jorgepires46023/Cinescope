package pt.isel.ps.cinescope.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pt.isel.ps.cinescope.controllers.models.UserInputModel
import pt.isel.ps.cinescope.services.UsersServices

@RestController
class UsersController(val usersService: UsersServices) {



    @PostMapping("/users")
    fun createUser(@RequestBody info: UserInputModel): ResponseEntity<*> {
        val user = usersService.createUser(info.name, info.email, info.password)

        return ResponseEntity
                .status(201)
                .body(user)
    }
}