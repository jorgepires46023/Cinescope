package pt.isel.ps.cinescope.repositories.database

interface TransactionManager {
    fun<R> run(block: (Transaction) -> R): R
}