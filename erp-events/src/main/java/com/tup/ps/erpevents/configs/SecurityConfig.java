package com.tup.ps.erpevents.configs;

import com.tup.ps.erpevents.enums.RoleName;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/ping")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/employees/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/suppliers/**")).hasRole(String.valueOf(RoleName.ADMIN))
                        .requestMatchers(new AntPathRequestMatcher("/tasks/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/clients/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/guests/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/locations/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/notifications/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/events/**")).hasRole(String.valueOf(RoleName.ADMIN))
                        .requestMatchers(new AntPathRequestMatcher("/users/**")).hasRole(String.valueOf(RoleName.ADMIN))
                        //.requestMatchers("/api/**").permitAll()
                        //.requestMatchers("/api-docs/swagger-config").permitAll()
                        //.requestMatchers(HttpMethod.GET,"/accounts/search").authenticated()
                        //.requestMatchers("/users").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
