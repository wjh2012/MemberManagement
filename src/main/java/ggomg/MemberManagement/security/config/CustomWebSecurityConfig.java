package ggomg.MemberManagement.security.config;


import ggomg.MemberManagement.security.OAuth2User.CustomAuthorizationRequestResolver;
import ggomg.MemberManagement.security.OAuth2User.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class CustomWebSecurityConfig {

    private final CustomAuthorizationRequestResolver customAuthorizationRequestResolver;
    private final CustomOAuth2UserService customOAuth2UserService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**", "/register/**", "/normal/**", "/error/**"
                                , "/css/**", "/images/**", "/js/**", "/console/**", "/favicon.ico/**")
                        .permitAll()
                        .requestMatchers("/member/oauth2").hasAuthority("OAUTH2_USER")
                        .requestMatchers("/member/oidc").hasAuthority("OIDC_USER")
                        .requestMatchers("/member/role").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                )
                .oauth2Login(login -> login
                        .loginPage("/login")
                        .authorizationEndpoint(authorize -> authorize
                                .authorizationRequestResolver(customAuthorizationRequestResolver)
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/")
                )
        ;

        return http.build();
    }
}
