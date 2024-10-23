package org.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.oauth2ResourceServer(oauthResourceServer -> oauthResourceServer
                        .jwt(jwtConfigurer -> {
                            jwtConfigurer.jwkSetUri("http://localhost:8081/oauth2/jwks");
                            jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter());
                        }))
                .build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {

            List<String> authorities = jwt.getClaimAsStringList("authorities");
            if (authorities == null) {
                authorities = Collections.emptyList();
            }
            Collection<GrantedAuthority> grantedAuthorities = new JwtGrantedAuthoritiesConverter().convert(jwt);
            grantedAuthorities.addAll(authorities.stream().map(SimpleGrantedAuthority::new).toList());
            return grantedAuthorities;
        });
        return jwtAuthenticationConverter;
    }
}
