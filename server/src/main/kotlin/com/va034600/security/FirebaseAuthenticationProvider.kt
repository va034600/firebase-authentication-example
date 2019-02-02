package com.va034600.security

import com.google.firebase.auth.FirebaseAuth
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.session.SessionAuthenticationException
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutionException

@Component
class FirebaseAuthenticationProvider(private val firebaseAuth: FirebaseAuth) : AbstractUserDetailsAuthenticationProvider() {
    override fun supports(authentication: Class<*>): Boolean {
        return FirebaseAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

    @Throws(AuthenticationException::class)
    override fun additionalAuthenticationChecks(userDetails: UserDetails,
                                                authentication: UsernamePasswordAuthenticationToken) {
    }

    @Throws(AuthenticationException::class)
    override fun retrieveUser(username: String, authentication: UsernamePasswordAuthenticationToken): UserDetails {
        val authenticationToken = authentication as FirebaseAuthenticationToken

        val task = firebaseAuth.verifyIdTokenAsync(authenticationToken.token)
        try {
            val token = task.get()
            return FirebaseUserDetails(token.email, token.uid)
        } catch (e: InterruptedException) {
            throw SessionAuthenticationException(e.message)
        } catch (e: ExecutionException) {
            throw SessionAuthenticationException(e.message)
        }

    }
}
