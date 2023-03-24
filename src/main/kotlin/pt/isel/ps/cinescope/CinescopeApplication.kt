package pt.isel.ps.cinescope

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CinescopeApplication

fun main(args: Array<String>) {
	runApplication<CinescopeApplication>(*args)
}
