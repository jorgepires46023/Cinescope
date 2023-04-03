package pt.isel.ps.cinescope.controllers

object Users {
    const val CREATE_USER = "/users"                // Register a new user.
    const val DELETE_USER = "/users/{id}"           // Deactivate user
    const val GET_USER_INFO = "users/{id}"          // Gets user info
    const val GET_USER_LISTS = "users/{id}/lists"   // Gets all user lists
}