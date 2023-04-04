package pt.isel.ps.cinescope.repositories.jdbi

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ps.cinescope.repositories.Transaction
import pt.isel.ps.cinescope.repositories.TransactionManager

@Component
class JdbiTransactionManager(
    private val jdbi: Jdbi
): TransactionManager {
    override fun <R> run(block: (Transaction) -> R): R {
        TODO("Not yet implemented")
    }
}