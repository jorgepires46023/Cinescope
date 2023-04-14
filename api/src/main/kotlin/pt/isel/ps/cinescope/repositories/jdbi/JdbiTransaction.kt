package pt.isel.ps.cinescope.repositories.jdbi


import org.jdbi.v3.core.Handle
import pt.isel.ps.cinescope.repositories.MoviesRepository
import pt.isel.ps.cinescope.repositories.Transaction
import pt.isel.ps.cinescope.repositories.UsersRepository

class JdbiTransaction(private val handle: Handle): Transaction {
    override val userRepository: UsersRepository by lazy {JdbiUsersRepository(handle)}

    override val moviesRepository: MoviesRepository by lazy {JdbiMoviesRepository(handle)}

    //override val seriesRepository: SeriesRepository by lazy {JdbiSeriesRepository(handle)}

    override fun rollback() {
        handle.rollback()
    }
}