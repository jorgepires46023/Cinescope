package pt.isel.ps.cinescope.controllers.models

data class UserInputModel(val name: String?, val email: String?, val password: String?)

data class LoginInputModel(val email: String?, val password: String?)

data class UserOutputModel(val name: String?, val email: String?)
