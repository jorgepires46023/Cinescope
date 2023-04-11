package pt.isel.ps.cinescope

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ps.cinescope.repositories.jdbi.configure
import pt.isel.ps.cinescope.repositories.jdbi.mappers.MovieStateMapper

@SpringBootApplication
class CinescopeApplication{
	final val userPostgres = System.getenv("PGUSER")
	final val passPostgres = System.getenv("PGPASSWORD")
	val jdbcLink = "jdbc:postgresql://localhost/postgres?user=$userPostgres&password=$passPostgres"
	@Bean
	fun jdbi() : Jdbi{
		val jdbcUrl = System.getenv("JDBC_DATABASE_URL")
			?: "jdbc:postgresql://localhost/postgres?user=postgres&password=postgres"

		val dataSource = PGSimpleDataSource()
		dataSource.setURL(jdbcUrl)

		return Jdbi.create(dataSource)
			.configure()
			.registerColumnMapper(MovieStateMapper())

	}
}

fun main(args: Array<String>) {
	runApplication<CinescopeApplication>(*args)
}
