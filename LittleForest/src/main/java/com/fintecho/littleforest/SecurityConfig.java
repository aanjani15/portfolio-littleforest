/*
 * package com.fintecho.littleforest;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityCustomizer; import
 * org.springframework.security.crypto.factory.PasswordEncoderFactories; import
 * org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.security.web.SecurityFilterChain; import
 * org.springframework.security.web.firewall.HttpFirewall; import
 * org.springframework.security.web.firewall.StrictHttpFirewall;
 * 
 * @EnableWebSecurity
 * 
 * @Configuration public class SecurityConfig { // 로그인 없이 접근 가능 경로 (인증 없이 허용)
 * private static final String[] PUBLIC_URLS = { "/" // root , "/image/**" //
 * 이미지 경로,"/css/**" // CSS파일들 , "/js/**" // , "/member/join" //회원가입 };
 * 
 * // springsecurity 무시 (자체 로그인 창이 자꾸 떠서 )
 * 
 * @Bean public WebSecurityCustomizer webSecurityCustomizer() { return web ->
 * web.ignoring().requestMatchers("/css/**", "/js/**", "/image/**",
 * "/fonts/**"); }
 * 
 * @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 * http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll() // 모든 요청을 인증
 * 없이 허용 ).formLogin(form -> form.disable()) // 기본 로그인 폼 비활성화 .httpBasic(basic
 * -> basic.disable()) // HTTP Basic 인증 비활성화 .csrf(csrf -> csrf.disable()) // 개발
 * 중일 경우 CSRF 비활성화 .logout(logout -> logout.logoutUrl("/member/logout") // 로그아웃
 * 
 * 요청 경로 (POST 요청) .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트 경로
 * .invalidateHttpSession(true) // 세션 무효화 .deleteCookies("JSESSIONID") //
 * JSESSIONID 쿠키 삭제 .permitAll());
 * 
 * return http.build(); }
 * 
 * @Bean public HttpFirewall allowUrlEncodedPercentHttpFirewall() {
 * StrictHttpFirewall firewall = new StrictHttpFirewall();
 * firewall.setAllowUrlEncodedPercent(true); // %는 인코딩
 * firewall.setAllowSemicolon(true); firewall.setAllowUrlEncodedSlash(true); //
 * 허용 return firewall; }
 * 
 * @Bean public PasswordEncoder passwordEncoder() { // BCrypt Encoder 사용 return
 * PasswordEncoderFactories.createDelegatingPasswordEncoder(); // return new
 * BCryptPasswordEncoder(); 보다 확장성 좋음 }
 * 
 * }
 */