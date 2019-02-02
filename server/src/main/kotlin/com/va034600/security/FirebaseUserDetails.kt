package com.va034600.security

import org.springframework.security.core.userdetails.User

class FirebaseUserDetails(email:String, uid:String): User(
        email,
        "",
        true,
        true,
        true,
        true,
        emptyList()) {
    val uid = uid
}