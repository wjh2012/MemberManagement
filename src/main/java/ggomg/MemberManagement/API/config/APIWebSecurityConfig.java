package ggomg.MemberManagement.API.config;

import ggomg.MemberManagement.security.LocalUser.CustomUserDetailsService;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class APIWebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final APIAuthenticationSuccessHandler successHandler;
    private final APIAuthenticationFailureHandler failureHandler;

    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(provider);
    }

    JSONUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() {

        JSONUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter = new JSONUsernamePasswordAuthenticationFilter();
        jsonUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/api/login");
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        jsonUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        return jsonUsernamePasswordAuthenticationFilter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(m -> m
                        .addObjectPostProcessor(new ObjectPostProcessor<CompositeSessionAuthenticationStrategy>() {
                            @Override
                            public <O extends CompositeSessionAuthenticationStrategy> O postProcess(O object) {
                                jsonUsernamePasswordAuthenticationFilter().setSessionAuthenticationStrategy(object);
                                return object;
                            }
                        }))
                .securityMatcher("/api/**")
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login/**")
                        .permitAll())
                .addFilterBefore(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://ggomg.net"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
