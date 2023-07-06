package pt.isel.ps.cinescope.repositories.database.jdbi


import org.jdbi.v3.core.Handle
import pt.isel.ps.cinescope.repositories.database.MoviesRepository
import pt.isel.ps.cinescope.repositories.database.SeriesRepository
import pt.isel.ps.cinescope.repositories.database.Transaction
import pt.isel.ps.cinescope.repositories.database.UsersRepository

class JdbiTransaction(private val handle: Handle): Transaction {
    override val userRepository: UsersRepository by lazy { JdbiUsersRepository(handle) }

    override val moviesRepository: MoviesRepository by lazy { JdbiMoviesRepository(handle) }

    override val seriesRepository: SeriesRepository by lazy { JdbiSeriesRepository(handle) }

    override fun rollback() {
        handle.rollback()
    }
}