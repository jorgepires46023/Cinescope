package pt.isel.ps.cinescope.repositories

interface Transaction {
    val userRepository: UsersRepository
    fun rollback()
}