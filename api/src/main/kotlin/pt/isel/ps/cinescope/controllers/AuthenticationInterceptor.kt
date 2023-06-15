package pt.isel.ps.cinescope.controllers

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import pt.isel.ps.cinescope.services.exceptions.BadRequestException

@Component
class AuthenticationInterceptor(private val tokenProcessor: TokenProcessor): HandlerInterceptor{
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val cookies = request.cookies
        val token = if(cookies !=null) cookies.find { c -> c.name == "userToken" } else null
        // enforce authentication
        //val token = request.getHeader(NAME_AUTHORIZATION_HEADER)
        return if (token == null) {
            response.status = 401
            //response.addHeader(NAME_WWW_AUTHENTICATE_HEADER, TokenProcessor.SCHEME)
            false
        } else {
            return token.value != null
        }
    }

    companion object {
        private const val NAME_AUTHORIZATION_HEADER = "Authorization"
        private const val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"
    }
}


