package pt.isel.ps.cinescope

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ps.cinescope.repositories.Transaction
import pt.isel.ps.cinescope.repositories.TransactionManager
import pt.isel.ps.cinescope.repositories.jdbi.JdbiTransaction
import pt.isel.ps.cinescope.repositories.jdbi.configure

private val jdbi = Jdbi.create(
    PGSimpleDataSource().apply {
        val jdbcUrl = System.getenv("JDBC_DATABASE_URL")
            ?: "jdbc:postgresql://localhost/postgres?user=postgres&password=postgres"
        setURL(jdbcUrl)
    }
).configure()

fun testWithHandleAndRollback(block: (Handle) -> Unit) = jdbi.useTransaction<Exception>{ handle ->
    block(handle)
    handle.rollback()
}

fun testWithTransactionManagerAndRollback(block: (TransactionManager) -> Unit) = jdbi.useTransaction<Exception>
{ handle ->

    val transaction = JdbiTransaction(handle)

    //a test TransactionManager that never commits
    val transactionManager = object : TransactionManager{
        override fun <R> run(block: (Transaction) -> R): R {
            return block(transaction)
            // n.b. no commit happens
        }
    }
    block(transactionManager)

    //finally, rollback everything
    handle.rollback()
}