package com.appteam.template.oauth;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import com.appteam.template.data.Token;
import com.appteam.template.dto.TokenData;
import com.appteam.template.service.TokenService;
import com.appteam.template.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.SpringVersion;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomOAuth2UserService oauthUserService;
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/**", "/login", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("pass")
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {

                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        System.out.println("AuthenticationSuccessHandler invoked");
                        System.out.println("Authentication name: " + authentication.getName());
                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                        userService.processOAuthPostLogin(oauthUser.getEmail());

                        TokenData tokenData = TokenData.generate(oauthUser.getEmail());
                        tokenService.saveToken(tokenData);
                        Cookie cookie = new Cookie("token" + tokenData.getKey().substring(0, 4), URLEncoder.encode(tokenData.toJSON().toString(), "UTF-8"));
                        cookie.setPath("/");
//                        URL urlToRedirect = new URL("http://localhost:8080/test/redirectedUrl");
//                        cookie.setDomain(urlToRedirect.getHost());
                        response.addCookie(cookie);
                        response.sendRedirect("/");
                        //response.sendRedirect("https://localhost:3000/home");
//                        response.setStatus(303);
                        //response.sendRedirect("https://oharadevelopershop.myshopify.com/admin/oauth/authorize?client_id=62c60904ece30e9454ebd81fccc7882c&scope=read_content%2Cwrite_content%2Cread_themes%2Cwrite_themes%2Cread_products%2Cwrite_products%2Cread_customers%2Cwrite_customers%2Cread_orders%2Cwrite_orders%2Cread_script_tags%2Cwrite_script_tags%2Cread_fulfillments%2Cwrite_fulfillments%2Cread_shipping%2Cwrite_shipping%2Cread_analytics&redirect_uri=http://localhost:8080/");
                    }
                })
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
        ;
    }
}
//http://localhost:8080/oauth2/authorization/google
//oharadevelopershop
//java-shop1