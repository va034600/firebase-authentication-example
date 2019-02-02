package com.va034600.security

import com.google.api.client.util.Strings
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FirebaseAuthenticationTokenFilter : AbstractAuthenticationProcessingFilter("/auth/**") {
    companion object {
        private const val TOKEN_HEADER = "Authorization"
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val authToken = request.getHeader(TOKEN_HEADER)
        if (Strings.isNullOrEmpty(authToken) || !authToken.startsWith("bearer ")) {
            throw AuthenticationServiceException("Invaild auth token")
        }

        return authenticationManager.authenticate(FirebaseAuthenticationToken(authToken))
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain?, authResult: Authentication) {
        super.successfulAuthentication(request, response, chain, authResult)

        chain!!.doFilter(request, response)
    }
}