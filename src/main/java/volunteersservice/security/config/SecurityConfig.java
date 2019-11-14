package volunteersservice.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .permitAll()
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
//                .permitAll()
                // указываем URL логаута
//                .logoutUrl("/logout")
                // указываем URL при удачном логауте
//                .logoutSuccessUrl("/main");
//              .logoutSuccessUrl("/login?logout");
                // делаем не валидной текущую сессию
//                .invalidateHttpSession(true);
    }
}