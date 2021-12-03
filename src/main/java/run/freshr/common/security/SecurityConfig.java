package run.freshr.common.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static run.freshr.common.config.URIConfig.uriAuthToken;
import static run.freshr.common.config.URIConfig.uriCommonHeartbeat;
import static run.freshr.common.config.URIConfig.uriDocsAll;
import static run.freshr.common.config.URIConfig.uriFavicon;
import static run.freshr.common.config.URIConfig.uriH2All;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers(uriH2All, uriDocsAll, uriFavicon)
        .antMatchers(GET, uriCommonHeartbeat)
        .antMatchers(POST, uriAuthToken);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .cors()
        .and()
        .sessionManagement()//세션 정책 설정
        .sessionCreationPolicy(STATELESS)
        .and()
        .authorizeRequests()
        .anyRequest()
        .permitAll()
        .and()
        .addFilterBefore(new SecurityFilter(),
            UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder authManager) {
    // This is the code you usually have to configure your authentication manager.
    // This configuration will be used by authenticationManagerBean() below.
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    // ALTHOUGH THIS SEEMS LIKE USELESS CODE,
    // IT'S REQUIRED TO PREVENT SPRING BOOT AUTO-CONFIGURATION
    return super.authenticationManagerBean();
  }

}
