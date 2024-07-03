package com.glos.api.authservice.config;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.client.UserDatabaseAPIClient;
import com.glos.api.authservice.util.security.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.anonymous(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(x -> {
            x.requestMatchers("/auth/admin/register").permitAll();
            x.requestMatchers("/auth/register", "/auth/login", "/auth/validate").permitAll();
            x.anyRequest().permitAll();
        });
        http.exceptionHandling(handling -> {
            handling.authenticationEntryPoint((request, response, exception) -> {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Unauthorized");
            });

            handling.accessDeniedHandler(((request, response, exception) -> {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("Forbidden");
            }));
        });
        http.logout(x -> {
            x.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            x.logoutSuccessHandler((request, response, authentication) ->
                    response.setStatus(HttpServletResponse.SC_OK));
            x.deleteCookies("JSESSIONID");
        });
        //http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            UserAPIClient userAPIClient,
            UserDatabaseAPIClient userDatabaseAPIClient
    ) {
        return new UserDetailsServiceImpl(userAPIClient, userDatabaseAPIClient);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
