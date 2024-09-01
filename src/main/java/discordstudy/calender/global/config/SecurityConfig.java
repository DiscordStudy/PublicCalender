package discordstudy.calender.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//Spring Security 에서 보안 설정을 활성화 하기 위해서 사용하는 애노테이션
public class SecurityConfig {
//시큐리티 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests((requests)->requests
                        .requestMatchers("/","/members/signup").permitAll()//
                        .anyRequest().authenticated()//그외의 모든 요청은 인증요구
                )
                .logout((logout) -> logout.permitAll()); // 로그아웃 접근도 모두 허용

                return http.build();
    }



}
