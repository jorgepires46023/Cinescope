package pt.isel.ps.cinescope.repositories

interface TransactionManager {
    fun<R> run(block: (Transaction) -> R): R
}