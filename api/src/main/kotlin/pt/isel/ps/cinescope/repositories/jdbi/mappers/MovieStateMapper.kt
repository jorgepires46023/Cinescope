package pt.isel.ps.cinescope.repositories.jdbi.mappers

import pt.isel.ps.cinescope.domain.MovieState
import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet
import java.sql.SQLException

class MovieStateMapper: ColumnMapper<MovieState> {
    @Throws(SQLException::class)
    override fun map(r: ResultSet, columnNumber: Int, ctx: StatementContext?): MovieState {
        return MovieState.fromString(r.getString(columnNumber))
    }
}