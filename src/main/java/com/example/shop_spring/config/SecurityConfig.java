package com.example.shop_spring.config;

import com.example.shop_spring.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;

//В данном классе описана вся информация о безопасности приложения
// @EnableWebSecurity применяет конфигурацию безопасности Spring по умолчанию к нашему приложению
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;


    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // Конфигурация Spring Security
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //отключаем защиту от межсайтовой подделки запросов
        httpSecurity
                //------------------------------------------------------------------------------//
                // Указываем что все страницы будут защищены процессом аутентиффикации
                .authorizeRequests()
                //------------------------------------------------------------------------------//
                // указываем что станица /admin доступна пользователю с ролью ADMIN
                .antMatchers("/admin").hasRole("ADMIN")
                //------------------------------------------------------------------------------//
//                // Указываем что данные страницы доступны всем пользователям
                .antMatchers("/authentication/login", "/authentication/registration", "/error","/product","/img/**","/product/info/{id}","/authentication/index","/category","/category/info/{id}","/category/product/{id}","/category/product").permitAll()
                //------------------------------------------------------------------------------//
                // Указываем каким пользователям доступны все остальные страницы
                .anyRequest().hasAnyRole("USER","ADMIN")
                //------------------------------------------------------------------------------//
//                // Указываем что для всех остальных страниц необходимо вызывать метод authenticated(), который открывает форму аутентификации
//                .anyRequest().authenticated()
                //------------------------------------------------------------------------------//
                //

                .and()
                //------------------------------------------------------------------------------//
                //Указываем на какой url адрес фильтр Spring Security будет отправлять не аутентифицированного пользователя при заходе на защищенную страницу
                .formLogin().loginPage("/authentication/index")
//                .formLogin().loginPage("/authentication/login")
                //------------------------------------------------------------------------------//
                // указываем на какой url будут отправлятся данные с формы аутентификации
                .loginProcessingUrl("/process_login")
                //------------------------------------------------------------------------------//
                // указываем на какой url направить пользователя после успешной аутентификации
                .defaultSuccessUrl("/index", true)  // true перенаправлять пользователя в любом случае
                //------------------------------------------------------------------------------//
                // Указываем куда необходимо перейти при неверной аутентификации
                .failureUrl("/authentication/login?error")
                //------------------------------------------------------------------------------//
                .and()
                .logout().logoutUrl("/authentication/index").logoutSuccessUrl("/authentication/index")
        ;

    }

    // Данный метод позволяет настроить аутентификацию
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        authenticationManagerBuilder.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/resources/**","/static/**","/css/**");
    }
}