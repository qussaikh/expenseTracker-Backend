package com.qussai.expenseTracker.config;

import com.qussai.expenseTracker.security.JwtAuthenticationEntryPoint;
import com.qussai.expenseTracker.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // URL patterns to be allowed without authentication.
    private static final String[] WHITE_LIST_URL = {
            "/api/auth/register",
            "/api/auth/admin-register",
            "/api/auth/login/**",
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> {
//                    authorize.requestMatchers(HttpMethod.POST, "/api/invoice").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER");
//                    authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("ADMIN", "USER");
//                      authorize.requestMatchers(HttpMethod.GET, "/api/invoice").permitAll();
//                      authorize.requestMatchers(HttpMethod.POST, "/api/**").permitAll();
//                      authorize.requestMatchers(HttpMethod.PUT, "/api/**").permitAll();
//                      authorize.requestMatchers(HttpMethod.PATCH, "/api/**").permitAll();
//                      authorize.requestMatchers(HttpMethod.DELETE, "/api/**").permitAll();

                    authorize.requestMatchers(HttpMethod.POST, "/api/invoice/").hasAnyRole("ADMIN");
                    authorize.requestMatchers(HttpMethod.PUT, "/api/invoice/").hasAnyRole("ADMIN", "USER");
                    authorize.requestMatchers(HttpMethod.GET, "/api/invoice/").hasAnyRole("ADMIN", "USER");
                    authorize.requestMatchers(HttpMethod.PATCH, "/api/invoice/").hasAnyRole("ADMIN", "USER");
                    authorize.requestMatchers(HttpMethod.DELETE, "/api/invoice/").hasAnyRole("ADMIN", "USER");
                    authorize.requestMatchers(WHITE_LIST_URL).permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());

        http.exceptionHandling( exception -> exception
                .authenticationEntryPoint(authenticationEntryPoint));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails ramesh = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(ramesh, admin);
//    }
}
