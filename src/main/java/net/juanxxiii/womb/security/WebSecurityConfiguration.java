package net.juanxxiii.womb.security;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
//requests do not require authorization
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;
    private static final String[] AUTH_WHITE_LIST = {
            "/womb/api/countries",
            "/womb/api/countries/**",
            "/womb/api/options",
            "/v2/api-docs/**",
            "/swagger.json",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**"
    };

    public WebSecurityConfiguration(@Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService, BCryptPasswordEncoder encoder) {
        this.bCryptPasswordEncoder = encoder;
        this.userDetailsService = userDetailsService;
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITE_LIST).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/womb/api/options").permitAll()
                .antMatchers(HttpMethod.POST, "/womb/api/syslogin").permitAll()
                .antMatchers(HttpMethod.POST, "/womb/api/login").permitAll()
                .antMatchers(HttpMethod.GET, "/womb/api/find/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/womb/api/find/email/**").permitAll()
                .antMatchers(HttpMethod.POST, "/womb/api/users").permitAll()
                .antMatchers(HttpMethod.POST, "/womb/system/users/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/womb/api/lastwombs").permitAll()
                .anyRequest().authenticated()
                .and().addFilter(new AuthenticationRequest(authenticationManager()))
                .addFilter(new Authorization(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
