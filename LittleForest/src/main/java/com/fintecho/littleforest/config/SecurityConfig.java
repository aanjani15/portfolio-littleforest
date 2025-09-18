package com.fintecho.littleforest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.reactive.function.client.WebClient;

import com.fintecho.littleforest.service.admin.AdminUserService;

import jakarta.servlet.http.HttpSession;

@Configuration
public class SecurityConfig {

	/** 🔑 비밀번호 암호화기 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Order(1)
	@Profile("dev")
	public SecurityFilterChain devAdminChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/admin/**").authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.csrf(csrf -> csrf.disable());
		return http.build();
	}

	@Bean
	@Order(1)
	@Profile("prod")
	public SecurityFilterChain adminChain(HttpSession session, HttpSecurity http, AdminUserService adminUserService) throws Exception {
		http.securityMatcher("/admin/**")
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/admin/admin-login", "/admin/css/**", "/admin/js/**", "/admin/img/**")
						.permitAll().anyRequest().hasRole("ADMIN"))
				.formLogin(form -> form.loginPage("/admin/admin-login").loginProcessingUrl("/admin/admin-login")
						.usernameParameter("username").passwordParameter("password")
						.successHandler((req, res, auth) -> {
							session.setAttribute("user_Id", 2);
							var cache = new HttpSessionRequestCache();
							SavedRequest saved = cache.getRequest(req, res);
							String target = (saved != null) ? saved.getRedirectUrl() : "/admin/members";
							new DefaultRedirectStrategy().sendRedirect(req, res, target);
						}).failureUrl("/admin/admin-login?error").permitAll())
				.logout(l -> l.logoutUrl("/admin/logout").logoutSuccessUrl("/admin/admin-login")
						.invalidateHttpSession(true).clearAuthentication(true).deleteCookies("JSESSIONID"))
				.userDetailsService(adminUserService).headers(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain appChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/tree", "/attendance/**", "/css/**", "/js/**", "/img/**", "/favicon.ico")
				.permitAll().anyRequest().permitAll()).formLogin(form -> form.disable())
				.httpBasic(basic -> basic.disable()).csrf(csrf -> csrf.disable());
		return http.build();
	}

	@Bean
	public HttpFirewall allowUrlEncodedPercentHttpFirewall() {
		StrictHttpFirewall firewall = new StrictHttpFirewall();
		firewall.setAllowUrlEncodedPercent(true);
		firewall.setAllowSemicolon(true);
		firewall.setAllowUrlEncodedSlash(true);
		return firewall;
	}

	
	
	 // 여기서부터 DeepL용 WebClient 빈 추가 / 08.31
    @Bean(name = "deeplWebClient")
    public WebClient deeplWebClient(@Value("${deepl.endpoint}") String endpoint) {
        // properties가 /v2/translate로 끝나므로 baseUrl만 추출
        String base = endpoint.replace("/v2/translate", "");
        return WebClient.builder()
                .baseUrl(base)
                .defaultHeader(HttpHeaders.USER_AGENT, "LittleForest/1.0") // UA 필요시 대비
                .build();
    }
    // 여기까지 추가 /08.31
    

}
