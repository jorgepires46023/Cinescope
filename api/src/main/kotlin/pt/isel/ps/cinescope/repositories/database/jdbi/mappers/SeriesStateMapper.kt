package pt.isel.ps.cinescope.repositories.database.jdbi.mappers

import org.jdbi.v3.core.mapper.ColumnMapper
import org.jdbi.v3.core.statement.StatementContext
import pt.isel.ps.cinescope.domain.SeriesState
import java.sql.ResultSet
import java.sql.SQLException

class SeriesStateMapper: ColumnMapper<SeriesState> {

    @Throws(SQLException::class)
    override fun map(r: ResultSet, columnNumber: Int, ctx: StatementContext?): SeriesState {
        return SeriesState.fromString(r.getString(columnNumber))
    }
}