package pt.isel.ps.cinescope.repositories.database

interface Transaction {
    val userRepository: UsersRepository
    val moviesRepository: MoviesRepository
    val seriesRepository: SeriesRepository
    fun rollback()
}