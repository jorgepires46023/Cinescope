package pt.isel.ps.cinescope.controllers

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthenticationInterceptor(private val tokenProcessor: TokenProcessor): HandlerInterceptor{
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // enforce authentication
        val token = request.getHeader(NAME_AUTHORIZATION_HEADER)
        val user = tokenProcessor.processToken(token)
        if (user == null) {
            response.status = 401
            response.addHeader(NAME_WWW_AUTHENTICATE_HEADER, TokenProcessor.SCHEME)
            return false
        } else {
            return true
        }
    }

    companion object {
        private const val NAME_AUTHORIZATION_HEADER = "Authorization"
        private const val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"
    }
}


