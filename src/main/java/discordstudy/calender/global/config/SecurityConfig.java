package discordstudy.calender.global.config;

import discordstudy.calender.global.config.jwt.JwtTokenFilter;
import discordstudy.calender.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//Spring Security 에서 보안 설정을 활성화 하기 위해서 사용하는 애노테이션
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    //시큐리티 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/members/*","/team").permitAll()//
                        .anyRequest().authenticated()//그외의 모든 요청은 인증요구
                )
                .logout(LogoutConfigurer::permitAll); // 로그아웃 접근도 모두 허용



        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();//Bcrypt 패스워드 인코딩 방식 사용 !
    }


}
