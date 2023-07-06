package pt.isel.ps.cinescope

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pt.isel.ps.cinescope.controllers.AuthenticationInterceptor
import pt.isel.ps.cinescope.repositories.database.jdbi.configure
import pt.isel.ps.cinescope.repositories.database.jdbi.mappers.MovieStateMapper

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

@Configuration
class Config(val authenticationInterceptor: AuthenticationInterceptor): WebMvcConfigurer{

	override fun addInterceptors(registry: InterceptorRegistry) {
		registry.addInterceptor(authenticationInterceptor).excludePathPatterns("/users", "/login", "/api_movies/**", "/api_series/**", "/search/**")
	}
}

fun main(args: Array<String>) {
	runApplication<CinescopeApplication>(*args)
}
