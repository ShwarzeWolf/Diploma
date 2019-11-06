package volunteersservice.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
        http.csrf().disable().authorizeRequests().antMatchers("/resources/**", "/**").permitAll().anyRequest()
                .permitAll().and();

        http.formLogin()
                // указываем страницу с формой логина
                .loginPage("/testapi/login") // FIXME remove /testapi when ready
                // указываем action с формы логина
                .loginProcessingUrl("/testapi/login") // FIXME remove /testapi when ready
                // Перенаправляем в /home после логина (?)
                .defaultSuccessUrl("/testapi/home") // FIXME remove /testapi when ready
                // указываем URL при неудачном логине
                .failureUrl("/testapi/login?error") // FIXME remove /testapi when ready
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("email").passwordParameter("password")
                // даем доступ к форме логина всем
                .permitAll();

        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutUrl("/testapi/logout") // FIXME remove /testapi when ready
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/testapi/login?logout") // FIXME remove /testapi when ready
                // делаем не валидной текущую сессию
                .invalidateHttpSession(true);

    }

}