package restservice.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import restservice.security.CustomAuthenticationProcessingFilter;
import restservice.security.CustomRESTAuthenticationManager;
import restservice.security.handlers.CustomAccessDeniedHandler;
import restservice.security.handlers.CustomAuthenticationFailureHandler;
import restservice.security.handlers.CustomAuthenticationSuccessHandler;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;


/**
 * Configuration class for Spring Security Framework
 */
@Configuration
@EnableWebSecurity
@ComponentScan("restservice.security")
@PropertySource("classpath:security.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.securedURLPattern}")
    private String securedUrlPattern;

    @Value("${security.tokenHeaderName}")
    private String tokenHeaderName;

    @Autowired
    private CustomRESTAuthenticationManager customRESTAuthenticationManager;

    /**
     * Configures Spring HttpSecurity for using with RESTful service. Defines stateless session creation policy, sets
     * necessary authentication for secured URL pattern, disables any web-related stuff like forms and adds
     * custom authentication filter to the Spring Security FilterChain.
     *
     * @param http spring restservice.security http context
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(securedUrlPattern).authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .addFilterBefore(customAuthenticationProcessingFilter(), BasicAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(getCustomAccessDeniedHandler());
    }

    @Bean
    public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler getCustomAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }


    /**
     * Defines AbstractAuthenticationProcessingFilter bean with configured AuthenticationManager and success/failure handlers
     */
    @Bean
    public AbstractAuthenticationProcessingFilter customAuthenticationProcessingFilter() {
        CustomAuthenticationProcessingFilter filter =
                new CustomAuthenticationProcessingFilter(securedUrlPattern, tokenHeaderName);
        filter.setAuthenticationManager(customRESTAuthenticationManager);
        filter.setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(getAuthenticationFailureHandler());
        return filter;
    }

    /**
     * Defines hexBinaryAdapter used for bi-directional converting byte array into UTF-8 string
     *
     * @return created adapter instance
     */
    @Bean
    public HexBinaryAdapter hexBinaryAdapter() {
        return new HexBinaryAdapter();
    }
}
