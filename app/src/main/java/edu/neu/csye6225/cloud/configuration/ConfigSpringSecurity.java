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
                .antMatchers("/","/resources/**" ,"/custom/**","/propic/**" ,"/username-check", "/mobile-check", "/register-player", "/register-player", "/log-in").permitAll()
                .antMatchers("/welcome-oadmin").hasAnyAuthority("OADMIN")
                .antMatchers("/get-newgrounds").hasAnyAuthority("OADMIN")
                .antMatchers("/enable-newgrounds").hasAnyAuthority("OADMIN")
                .antMatchers("/welcome-gcrew").hasAnyAuthority("GCREW")
                .antMatchers("/add-ground").hasAnyAuthority("GCREW")
                .antMatchers("/next-dates").hasAnyAuthority("GCREW")
                .antMatchers("/get-availmap").hasAnyAuthority("GCREW")
                .antMatchers("/update-timeslot").hasAnyAuthority("GCREW")
                .antMatchers("/welcome-player").hasAnyAuthority("PLAYER")
                .antMatchers("/teamname-check").hasAnyAuthority("PLAYER")
                .antMatchers("/create-team").hasAnyAuthority("PLAYER")
                .antMatchers("/get-myteams").hasAnyAuthority("PLAYER")
                .antMatchers("/search-team").hasAnyAuthority("PLAYER")
                .antMatchers("/join-team").hasAnyAuthority("PLAYER")
                .antMatchers("/in-team").hasAnyAuthority("PLAYER")
                .antMatchers("/upload-pic").hasAnyAuthority("PLAYER")
                .antMatchers("/get-joinrequests").hasAnyAuthority("PLAYER")
                .antMatchers("/accept-joinrequests").hasAnyAuthority("PLAYER")
                .antMatchers("/reject-joinrequests").hasAnyAuthority("PLAYER")
                .antMatchers("/get-oldplayers").hasAnyAuthority("PLAYER")
                .antMatchers("/remove-oldplayer").hasAnyAuthority("PLAYER")
                .antMatchers("/create-poll").hasAnyAuthority("PLAYER")
                .antMatchers("/get-activepolls").hasAnyAuthority("PLAYER")
                .antMatchers("/close-poll").hasAnyAuthority("PLAYER")
                .antMatchers("/join-activepolls").hasAnyAuthority("PLAYER")
                .and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/log-in")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/welcome", false)
                .failureUrl("/?error=true")
                .and()
                .exceptionHandling().accessDeniedPage("/access-issue")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/?logout")
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
