package pt.isel.ps.cinescope.repositories.jdbi

import org.jdbi.v3.core.Jdbi
import org.postgresql.util.PSQLException
import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.repositories.Transaction
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.services.exceptions.InternalServerErrorException

@Component
class JdbiTransactionManager(
    private val jdbi: Jdbi
): TransactionManager {
    override fun <R> run(block: (Transaction) -> R): R =
        try {
            jdbi.inTransaction<R, Exception>{ handle ->
                try {
                    val transaction = JdbiTransaction(handle)
                    block(transaction)
                } catch (e: PSQLException) {
                    throw InternalServerErrorException(e.localizedMessage ?: "database")
                }
            }
        } catch(e: Exception) {
            throw e
        }

    }
