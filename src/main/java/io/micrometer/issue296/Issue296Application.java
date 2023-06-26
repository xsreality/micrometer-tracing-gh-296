package io.micrometer.issue296;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.SupplierReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Hooks;

@Configuration
@EnableWebFluxSecurity
@SpringBootApplication
public class Issue296Application {

    public static void main(String[] args) {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(Issue296Application.class, args);
    }

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(spec -> spec.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        NimbusReactiveJwtDecoder.JwkSetUriReactiveJwtDecoderBuilder builder = NimbusReactiveJwtDecoder.withJwkSetUri("http://localhost:8090");
        return new SupplierReactiveJwtDecoder(builder::build);
    }

}
