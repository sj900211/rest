package run.freshr.common.security;

import static run.freshr.common.config.URIConfig.uriAuthToken;
import static run.freshr.common.config.URIConfig.uriCommonHeartbeat;
import static run.freshr.common.config.URIConfig.uriDocsAll;
import static run.freshr.common.config.URIConfig.uriEditorAll;
import static run.freshr.common.config.URIConfig.uriEnumAll;
import static run.freshr.common.config.URIConfig.uriFavicon;
import static run.freshr.common.config.URIConfig.uriFileAll;
import static run.freshr.common.config.URIConfig.uriH2All;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

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

/**
 * The Class Security config.
 *
 * @author [류성재]
 * @implNote Security 설정
 * @since 2021. 2. 25. 오전 10:37:04
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Password encoder password encoder.
   *
   * @return the password encoder
   * @author [류성재]
   * @implNote 비밀번호 암호화 방식 설정
   * @since 2021. 3. 16. 오전 11:55:17
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configure.
   *
   * @param web the web
   * @author [류성재]
   * @implNote 권한 체크에서 제외할 항목 설정
   * @since 2021. 2. 25. 오전 10:37:04
   */
  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers(uriH2All, uriDocsAll, uriFavicon, uriEnumAll, uriFileAll, uriEditorAll)
        .antMatchers(GET, uriCommonHeartbeat)
        .antMatchers(POST, uriAuthToken);
  }

  /**
   * Configure.
   *
   * @param http the http
   * @throws Exception the exception
   * @author [류성재]
   * @implNote Security 설정
   * @since 2021. 2. 25. 오전 10:37:04
   */
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

  /**
   * Configure.
   *
   * @param authManager the auth manager
   * @author [류성재]
   * @implNote
   * @since 2021. 2. 25. 오전 10:37:04
   */
  @Override
  protected void configure(AuthenticationManagerBuilder authManager) {
    // This is the code you usually have to configure your authentication manager.
    // This configuration will be used by authenticationManagerBean() below.
  }

  /**
   * Authentication manager bean authentication manager.
   *
   * @return the authentication manager
   * @throws Exception the exception
   * @author [류성재]
   * @implNote
   * @since 2021. 2. 25. 오전 10:37:04
   */
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    // ALTHOUGH THIS SEEMS LIKE USELESS CODE,
    // IT'S REQUIRED TO PREVENT SPRING BOOT AUTO-CONFIGURATION
    return super.authenticationManagerBean();
  }

}
