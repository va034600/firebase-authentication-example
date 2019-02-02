package com.va034600.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
        private val authenticationProvider: FirebaseAuthenticationProvider
) : WebSecurityConfigurerAdapter() {

    @Bean
    @Throws(Exception::class)
    public override fun authenticationManager(): AuthenticationManager {
        return ProviderManager(Arrays.asList<AuthenticationProvider>(authenticationProvider))
    }

    @Throws(Exception::class)
    fun authenticationTokenFilterBean(): FirebaseAuthenticationTokenFilter {
        val authenticationTokenFilter = FirebaseAuthenticationTokenFilter()
        authenticationTokenFilter.setAuthenticationManager(authenticationManager())
        authenticationTokenFilter.setAuthenticationSuccessHandler { _, _, _ -> }
        return authenticationTokenFilter
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        web!!.ignoring()
                .antMatchers(HttpMethod.OPTIONS)
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/auth/**").authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter::class.java)
    }
}
