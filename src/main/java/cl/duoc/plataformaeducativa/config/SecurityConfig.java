package cl.duoc.plataformaeducativa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth

                        // Endpoint técnico
                        .requestMatchers("/error").permitAll()

                        // CURSOS
                        // Ambos roles pueden consultar
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/cursos/**"
                        ).hasAnyRole("ESTUDIANTE", "INSTRUCTOR")

                        // Solo el instructor administra cursos
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/cursos/**"
                        ).hasRole("INSTRUCTOR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/cursos/**"
                        ).hasRole("INSTRUCTOR")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/cursos/**"
                        ).hasRole("INSTRUCTOR")

                        // INSCRIPCIONES
                        .requestMatchers("/api/inscripciones/**")
                        .hasRole("ESTUDIANTE")

                        // BFF
                        .requestMatchers("/api/bff/**")
                        .hasRole("ESTUDIANTE")

                        // RABBITMQ
                        .requestMatchers("/api/resumenes-mq/**")
                        .hasAnyRole("ESTUDIANTE", "INSTRUCTOR")

                        // CONTENIDOS
                        // Ambos roles pueden consultar
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/contenidos/**"
                        ).hasAnyRole("ESTUDIANTE", "INSTRUCTOR")

                        // Solo el instructor administra contenidos
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/contenidos/**"
                        ).hasRole("INSTRUCTOR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/contenidos/**"
                        ).hasRole("INSTRUCTOR")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/contenidos/**"
                        ).hasRole("INSTRUCTOR")

                        // EVALUACIONES
                        // El estudiante envía sus respuestas
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/evaluaciones/*/respuestas"
                        ).hasRole("ESTUDIANTE")

                        // Ambos roles consultan evaluaciones del curso
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/evaluaciones/curso/**"
                        ).hasAnyRole("ESTUDIANTE", "INSTRUCTOR")

                        // Solo el instructor consulta resultados
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/evaluaciones/*/resultados"
                        ).hasRole("INSTRUCTOR")

                        // Solo el instructor crea evaluaciones
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/evaluaciones/curso/**"
                        ).hasRole("INSTRUCTOR")

                        // Solo el instructor califica resultados
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/evaluaciones/resultados/**"
                        ).hasRole("INSTRUCTOR")

                        // Solo el instructor actualiza evaluaciones
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/evaluaciones/**"
                        ).hasRole("INSTRUCTOR")

                        // Solo el instructor elimina evaluaciones
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/evaluaciones/**"
                        ).hasRole("INSTRUCTOR")

                        // RESÚMENES Y ARCHIVOS S3
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/resumenes/**"
                        ).hasAnyRole("ESTUDIANTE", "INSTRUCTOR")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/resumenes/**"
                        ).hasRole("INSTRUCTOR")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/resumenes/**"
                        ).hasRole("INSTRUCTOR")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/resumenes/**"
                        ).hasRole("INSTRUCTOR")

                        // Cualquier otro endpoint exige autenticación
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter
                                )
                        )
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter rolesConverter =
                new JwtGrantedAuthoritiesConverter();

        rolesConverter.setAuthoritiesClaimName("roles");
        rolesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter authenticationConverter =
                new JwtAuthenticationConverter();

        authenticationConverter.setJwtGrantedAuthoritiesConverter(
                rolesConverter
        );

        return authenticationConverter;
    }
}