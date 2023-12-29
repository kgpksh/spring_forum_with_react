package learning.practice.spring_forum_with_react.base.security;

import learning.practice.spring_forum_with_react.base.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecureConfig {
    @Value("${custom.jwt.secretKey}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.
                httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable()).
                csrf(csrfConfigurer -> csrfConfigurer.disable()).
                authorizeHttpRequests(request -> {
                    request.requestMatchers(
                            new AntPathRequestMatcher("/member/signup")
                            ,new AntPathRequestMatcher("/member/login"))
                            .anonymous();

                    request.requestMatchers(
                            new AntPathRequestMatcher("/post/list")
                            ,new AntPathRequestMatcher("/category/categoryList")
                            , new AntPathRequestMatcher("/post/view")
                    ).permitAll();

                    request.requestMatchers(
                            new AntPathRequestMatcher("/post/post")
                            , new AntPathRequestMatcher(("/post/comment"))
                    ).authenticated();
                }).
                sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }).
                addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class).
                build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
