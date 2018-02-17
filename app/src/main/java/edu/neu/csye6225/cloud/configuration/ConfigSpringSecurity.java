package edu.neu.csye6225.cloud.configuration;

import edu.neu.csye6225.cloud.filter.CsrfHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class ConfigSpringSecurity extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers("/","/css/**","/profilepics/**" ,"/images/**", "/js/**", "/scss/**","/email-check", "/register-user", "/register-confirmation", "/log-in").permitAll()
        .antMatchers("/welcome").hasAnyAuthority("USER")
        .and()
        .formLogin()
        .loginPage("/")
        .loginProcessingUrl("/log-in")
        .usernameParameter("useremail")
        .passwordParameter("password")
        .defaultSuccessUrl("/welcome", false)
        .failureUrl("/?error=true")
        .and()
        .exceptionHandling().accessDeniedPage("/access-issue")
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
        .logoutSuccessUrl("/")
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true);

        // csrf token for rest calls
        http
        .csrf().csrfTokenRepository(csrfTokenRepository())
        .and()
        .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);

        // disable cache after logout
        http
        .headers()
        .defaultsDisabled()
        .cacheControl();

    }

    /* Csrf Token for REST call from angular */
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
