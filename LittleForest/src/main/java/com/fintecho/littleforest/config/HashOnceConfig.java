
/*package com.fintecho.littleforest.config;
  
import org.springframework.boot.CommandLineRunner; import
org.springframework.context.annotation.Bean; import
org.springframework.context.annotation.Configuration; import
org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // ← 중요!
  
@Configuration public class HashOnceConfig {
  
@Bean CommandLineRunner printBcryptOnce() { return args -> { String raw =
"pass1232"; // 2번 계정의 현재 비번
  
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); String hash =
encoder.encode(raw);
  
System.out.println("\n===== BCrypt hash (" + raw + ") =====");
System.out.println(hash);
System.out.println("===== copy & then DELETE HashOnceConfig =====\n"); }; } }
*/
