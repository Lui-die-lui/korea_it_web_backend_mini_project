package com.korit.BoardStudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); // 다른곳에서 다 불러올 수 있음
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern(CorsConfiguration.ALL);

        // 요청을 보내는 쪽에서 Request, Response Header 정보에 대한 제약을 허용
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);

        // 요청을 보내는 쪽의 메소드 (GET, POST, PUT, DELETE, OPTION 등) 허용
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);

        // CORS 설정을 URL 경로에 적용하기 위한 객체 생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 모든 URL(/**)에 대해 위에서 설정한 CORS 정책을 허용
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()); //위에서 만든 cors 설정을 security에 적용
        http.csrf(csrf -> csrf.disable());

        //CSRF란
        //사용자가 의도하지 않은 요청을 공격자가 유도해서 서버에 전달하도록 하는 공격
        //JWT 방식 또는 무상태(Stateless) 인증이기 때문에
        //세션이 없고, 쿠키도 안 쓰고, 토큰 기반이기 때문에 CSRF 공격 자체가 성립되지 않는다.

//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // jwtfilter 작업 후 활성화

        //서버 사이드 렌더링 로그인 방식 비활성화
        http.formLogin(formLogin -> formLogin.disable());
        //HTTP 프로토콜 기본 로그인 방식 비활성화
        http.httpBasic(httpBasic -> httpBasic.disable());
        //서버 사이드 렌더링 로그아웃 비활성화
        http.logout(logout -> logout.disable());
        http.sessionManagement(Session -> Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth ->{
            auth.requestMatchers("").permitAll(); // 필터 그냥 통과
            auth.anyRequest().authenticated(); // 모든 요청은 필터를 거쳐야 함
        });

        return http.build();
    }
}
