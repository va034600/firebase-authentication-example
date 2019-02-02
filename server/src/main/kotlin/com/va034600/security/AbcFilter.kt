package com.va034600.security

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class AbcFilter: AbstractPreAuthenticatedProcessingFilter() {
    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest?): Any {
        return "";
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest?): Any {
        val credentials = request!!.getHeader("Authorization")
        return if (credentials == null) "" else credentials
    }

}
