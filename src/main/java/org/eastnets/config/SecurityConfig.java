package org.eastnets.config;

import org.eastnets.service.user.CustomUserDetailsService;
import org.eastnets.securityutil.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for setting up Spring Security in the application.
 * <p>
 * This configuration class:
 * <ul>
 *     <li>Sets up user authentication using the custom user details service.</li>
 *     <li>Configures JWT-based security by adding a JWT filter.</li>
 *     <li>Defines password encoding strategy (no encoding in this case).</li>
 *     <li>Configures security settings to permit access to specific endpoints and apply JWT authentication to other requests.</li>
 * </ul>
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Configures the authentication manager with the custom user details service.
     * <p>
     * This method sets up the user details service for authentication.
     *
     * @param auth the {@link AuthenticationManagerBuilder} used to build the authentication manager.
     * @throws Exception if an error occurs during authentication configuration.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    /**
     * Bean definition for the password encoder.
     * <p>
     * Returns a {@link NoOpPasswordEncoder} instance, which does not perform any password encoding.
     *
     * @return a {@link PasswordEncoder} instance configured to not encode passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Bean definition for the authentication manager.
     * <p>
     * Exposes the authentication manager bean for use in the application context.
     *
     * @return the {@link AuthenticationManager} instance.
     * @throws Exception if an error occurs while creating the authentication manager bean.
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Configures HTTP security settings.
     * <p>
     * Disables CSRF protection, permits access to specific endpoints (e.g., login, signup, Swagger documentation),
     * and requires authentication for all other requests. Configures stateless session management and adds a JWT filter
     * for handling authentication.
     *
     * @param http the {@link HttpSecurity} object used to configure HTTP security.
     * @throws Exception if an error occurs during HTTP security configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/signup", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/swagger-ui/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
