package com.va034600.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class FirebaseAuthenticationToken(token: String) : UsernamePasswordAuthenticationToken(null, null) {
    val token: String = token.replace("bearer ", "")

}
