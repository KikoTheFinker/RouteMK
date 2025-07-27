package mk.route.routemk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationProvider authProvider;

    public SecurityConfig(AuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(
                        headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                ).authorizeHttpRequests(a -> a.requestMatchers("**").permitAll());
//                .authorizeHttpRequests(
//                        authorizeRequests ->
//                                authorizeRequests
//                                        .requestMatchers("/", "/home",
//                                                "/login",
//                                                "/css/**",
//                                                "/js/**",
//                                                "/images/**",
//                                                "/register",
//                                                "/search-routes",
//                                                "/trips/{routeId:\\d+}")
//                                        .permitAll()
//                                        .requestMatchers("/routes/company/**").hasAnyRole("TRANSPORT_ORGANIZER", "DRIVER")
//                                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                                        .requestMatchers("/trips/user/**").hasRole("USER")
//                                        .anyRequest().authenticated()
//                )
//                .formLogin(login ->
//                        login.loginPage("/login")
//                                .permitAll()
//                                .defaultSuccessUrl("/")
//                                .failureUrl("/login?error")
//                )
//                .logout(
//                        logout ->
//                                logout.permitAll()
//                                        .logoutSuccessUrl("/")
//                                        .deleteCookies("JSESSIONID")
//                                        .invalidateHttpSession(true)
//                                        .clearAuthentication(true)
//                );

        return http.build();

    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }
}
