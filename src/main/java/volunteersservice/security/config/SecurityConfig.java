package volunteersservice.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import volunteersservice.security.PasswordEncoderImpl;
import volunteersservice.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsServiceImpl()).passwordEncoder(new PasswordEncoderImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/registration", "/main/**").permitAll()
                .antMatchers("/css/**").permitAll()
//                TODO: в версии продакшн убрать доступ к showapi
                .antMatchers("/showapi/**").permitAll()
                .antMatchers("/showapi/*").permitAll()
                .antMatchers(HttpMethod.POST, "/main/**").permitAll()
                .antMatchers(HttpMethod.POST, "/main*").permitAll()
                .anyRequest().authenticated()
            .and()
                .csrf().disable() // FIXME maybe? This solves the problem of POST-requests sometimes
            // .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("login")
                .failureUrl("/login-error")
                .passwordParameter("password")
                .permitAll()
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
    }
}