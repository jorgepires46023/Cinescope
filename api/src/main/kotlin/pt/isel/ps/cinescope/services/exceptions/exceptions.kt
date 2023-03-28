package pt.isel.ps.cinescope.services.exceptions

data class BadRequestException(val msg: String) : Exception()

data class UnauthorizedException(val msg: String) : Exception()

data class InternalServerErrorException(val msg: String) : Exception()

data class NotFoundException(val msg: String) : Exception()