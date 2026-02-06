package com.hekimasist.backend.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.hekimasist.backend.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

  private final CustomUserDetailsService userDetailsService;
  private final JwtAuthenticationFilter jwtAuthFilter;

  public SecurityConfig(CustomUserDetailsService userDetailsService,
      JwtAuthenticationFilter jwtAuthFilter) {
    this.userDetailsService = userDetailsService;
    this.jwtAuthFilter = jwtAuthFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(auth -> auth
            // 1. CORS Preflight isteklerine her zaman izin ver
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            // 2. Auth (Login/Register) işlemleri herkese açık
            .requestMatchers("/api/auth/**").permitAll()

            // 3. [GÜNCELLENDİ] Şikayet Listesi (Complaints)
            // Randevu oluşturulurken bu listeye ihtiyaç duyulduğu için erişim izni veriyoruz
            .requestMatchers("/api/complaints/**").hasAnyAuthority("DOKTOR", "ADMIN")

            // 4. Doktor işlemleri
            .requestMatchers("/api/doctors/**").hasAnyAuthority("DOKTOR", "ADMIN")

            // 5. Hasta ve Randevu işlemleri
            .requestMatchers("/api/patients/**").hasAnyAuthority("DOKTOR", "ADMIN")
            .requestMatchers("/api/appointments/**").hasAnyAuthority("DOKTOR", "ADMIN")

            // 6. Geri kalan tüm istekler için login şart
            .anyRequest().authenticated())

        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // Frontend portlarını buraya ekledik
    configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174"));
    configuration
        .setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

    // Authorization header'ı ve Content-Type çok kritik
    configuration.setAllowedHeaders(
        Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Accept",
            "X-Requested-With"));

    configuration.setExposedHeaders(List.of("Authorization"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}