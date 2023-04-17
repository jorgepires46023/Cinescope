package pt.isel.ps.cinescope.repositories

interface Transaction {
    val userRepository: UsersRepository
    val moviesRepository: MoviesRepository
    val seriesRepository: SeriesRepository
    fun rollback()
}