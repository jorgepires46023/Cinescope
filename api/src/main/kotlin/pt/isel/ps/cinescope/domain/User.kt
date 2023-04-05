package pt.isel.ps.cinescope.domain

import java.util.UUID

data class User(val id: Int?, val name: String?, val email: String?, val password: String?, val token: UUID?, val state: UserState?)

enum class UserState {
    Active(),
    Inactive();
}