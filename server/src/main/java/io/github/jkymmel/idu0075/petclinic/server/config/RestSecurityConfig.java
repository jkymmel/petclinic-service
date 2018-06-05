package io.github.jkymmel.idu0075.petclinic.server.config;

import io.github.jkymmel.idu0075.petclinic.server.security.ApiKeyAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String AUTHENTICATION_HEADER = "Authorization";

    @Resource
    private SecurityConfig securityConfig;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ApiKeyAuthenticationFilter filter = new ApiKeyAuthenticationFilter(AUTHENTICATION_HEADER);
        filter.setAuthenticationManager(authentication -> {
            String providedKey = (String) authentication.getPrincipal();
            if (!securityConfig.getKey().equals(providedKey)) {
                throw new BadCredentialsException("API key does not match.");
            }
            authentication.setAuthenticated(true);
            return authentication;
        });
        httpSecurity
                .requestMatchers()
                .antMatchers("/pet/**", "/veterinarian/**")
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
    }

}