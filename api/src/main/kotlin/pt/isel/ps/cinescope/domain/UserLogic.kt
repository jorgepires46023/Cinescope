package pt.isel.ps.cinescope.domain

fun validatePassword(userPassword: String?, inputPassword: String) : Boolean{
    return userPassword == inputPassword
}