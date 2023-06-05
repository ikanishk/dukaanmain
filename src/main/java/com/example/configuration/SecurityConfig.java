package com.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.model.CustomUserDetail;
import com.example.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
@Autowired
GoogleoAuthsuccesshandler googleoAuthsuccesshandler;

@Autowired
CustomUserDetailService customUserDetailService;
	
    // Configure security rules for different HTTP requests
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // Allow access to certain URLs without authentication
            .antMatchers("/", "/shop/**", "/register").permitAll()
            // Allow access to URLs starting with "/admin" only for users with "ADMIN" role
            .antMatchers("/admin/**").hasRole("ADMIN")
            // For all other URLs, require authentication
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login") // Specify the custom login page
            .permitAll()
            .failureUrl("/login?error=true") // Redirect to this URL in case of login failure
            .defaultSuccessUrl("/") // Redirect to this URL after successful login
            .usernameParameter("email") // Specify the parameter name for the username field in the login form
            .passwordParameter("password") // Specify the parameter name for the password field in the login form
            .and()
            .oauth2Login()
            .loginPage("/login") // Specify the custom login page for OAuth2 login
            .successHandler(googleoAuthsuccesshandler) // Custom success handler for OAuth2 login
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Specify the URL for logout
            .logoutSuccessUrl("/login") // Redirect to this URL after successful logout
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID") // Delete the specified cookies upon logout
            .and()
            .exceptionHandling()
            .and()
            .csrf()
            .disable(); // Disable CSRF protection
    }

    // Bean for password encoder used for encryption
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure the authentication manager to use the custom user detail service
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }

    // Configure web security to ignore authentication for static files
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/productImages/**", "/css/**", "/js/**");
    }

}
