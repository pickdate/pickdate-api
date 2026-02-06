package com.pickdate.iam;

import com.pickdate.shared.model.Username;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer.SessionFixationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;
import static org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED;


@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
class Security {

    private final UserRepository userRepository;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/reset-password").permitAll()
                        .requestMatchers("/api/v1/iam/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/**").hasAuthority("USER")
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .failureHandler((request, response, exception) -> {
                            var defaultFailureHandler = new SimpleUrlAuthenticationFailureHandler("/login?error");
                            var redirectStrategy = new DefaultRedirectStrategy();
                            if (exception instanceof CompromisedPasswordException) {
                                redirectStrategy.sendRedirect(request, response, "/reset-password");
                                return;
                            }
                            defaultFailureHandler.onAuthenticationFailure(request, response, exception);
                        })
                )
                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(IF_REQUIRED)
                        .sessionFixation(SessionFixationConfigurer::newSession)
                )
//                .rememberMe(configurer -> configurer
//                        .rememberMeServices(rememberMeServices()))
        ;


        return http.build();
    }

    @Bean
    CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

//    @Bean
//    SpringSessionRememberMeServices rememberMeServices() {
//        var rememberMeServices = new SpringSessionRememberMeServices();
//        rememberMeServices.setAlwaysRemember(true);
//        return rememberMeServices;
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        var authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        var providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return (username) -> userRepository.findById(Username.of(username))
                .map(User::toUserDetails)
                .stream()
                .peek(userDetails -> log.debug("get user details: {}", userDetails))
                .findFirst()
                .orElseThrow(() -> UserNotFound.withUsername(username));
    }

    @Bean
    AuthenticationEventPublisher authenticationEventPublisher(
            ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
}
