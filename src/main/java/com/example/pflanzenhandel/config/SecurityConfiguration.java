package com.example.pflanzenhandel.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configures web security for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception in case of configuration errors
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        // define all URLs which should be accessible without login
                        auth -> auth
                                .requestMatchers("/register", "/login").permitAll()
                                // define all URLs which require an authenticated user with a certain role
                                // NOTE: Spring Security automatically adds "ROLE_" while performing this check. For this reason we do not
                                // have to use "ROLE_ADMIN" here, which we define in the TestDatabaseLoader.
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                // all other URLs (except the ones above) require authentication too
                                .anyRequest().authenticated())
                // include CSRF token, which may be required while performing AJAX-requests
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/console/**"))
                // define the login-page, which is also accessible for everyone

                .formLogin( formLogin -> formLogin
                        .loginPage("/login").failureUrl("/login?error=true").permitAll()
                        .defaultSuccessUrl("/", true)
                        .usernameParameter("username")
                        .passwordParameter("password"))
                // everyone may logout

                .logout(logout -> logout.permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout"))

                //Disables header security. This allows the use of the h2 console.
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/access-denied"));

        return http.build();
    }
    /**
     * Configures web security to ignore requests to certain paths.
     *
     * @return a WebSecurityCustomizer that configures the ignored requests
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                // gewähre immer Zugriff auf Dateien in den folgenden Ordnern
                .requestMatchers("/resources/**", "/static/**", "/static/css/**", "/js/**", "/images/**");
    }

    /**
     * Password encoder bean for Spring Security. Required for encrypting passwords.
     *
     * @return a BCryptPasswordEncoder bean
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
