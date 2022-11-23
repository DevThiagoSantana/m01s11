package tech.devinhouse.livraria.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.devinhouse.livraria.service.TokenService;
import tech.devinhouse.livraria.service.UsuarioService;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private UsuarioService usuarioService;
    private TokenService tokenService;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder, UserDetailsService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AutorizacaoFilter autorizacaoFilter = new AutorizacaoFilter(tokenService);
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/autenticacao/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/livros/**").hasAnyAuthority("ROLE_LEITOR", "ROLE_FUNCIONARIO", "ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/api/livros/**").hasAnyAuthority("ROLE_FUNCIONARIO", "ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/livros/**").hasAnyAuthority("ROLE_FUNCIONARIO", "ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/livros/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(autorizacaoFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        }

    }
