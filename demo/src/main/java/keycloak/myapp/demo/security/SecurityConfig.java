package keycloak.myapp.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(t -> t.disable())
                .authorizeHttpRequests(a -> {
                    a.anyRequest().authenticated();
//                    a.requestMatchers(HttpMethod.GET, "/restaurant/**").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/order/**").permitAll()
//                            .anyRequest().authenticated();
                }).oauth2ResourceServer(o -> {
                    //o.jwt(Customizer.withDefaults());
                    //o.jwt(configurer -> configurer.jwtAuthenticationConverter(this.jwtAuthConverter));
                    o.opaqueToken(Customizer.withDefaults());
                }).sessionManagement(
                    s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

//    @Bean
//    public DefaultMethodSecurityExpressionHandler mSecurity() {
//        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
//                new DefaultMethodSecurityExpressionHandler();
//        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
//        return defaultMethodSecurityExpressionHandler;
//    }
}