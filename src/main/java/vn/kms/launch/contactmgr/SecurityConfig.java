package vn.kms.launch.contactmgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import vn.kms.launch.contactmgr.service.security.UserDetailsServiceImpl;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/").permitAll();
        http.csrf().disable();
        http.addFilterBefore(new AuthenticationFilter(authenticationManager()),
                BasicAuthenticationFilter.class);
        http.userDetailsService(userDetailsService);
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(domainUsernamePasswordAuthenticationProvider()).
        // authenticationProvider(backendAdminUsernamePasswordAuthenticationProvider()).
         authenticationProvider(tokenAuthenticationProvider());
    }

    private AuthenticationProvider tokenAuthenticationProvider() {
        return new TokenAuthenticationProvider(tokenService());
    }

    @Bean
    public TokenService tokenService() {
        return new TokenService();
    }

    @Bean
    public ExternalServiceAuthenticator someExternalServiceAuthenticator() {
        return new SomeExternalServiceAuthenticator();
    }

    @Bean
    public AuthenticationProvider domainUsernamePasswordAuthenticationProvider() {
        // TODO Auto-generated method stub
        return new DomainUsernamePasswordAuthenticationProvider(tokenService(),someExternalServiceAuthenticator());
    }

}
