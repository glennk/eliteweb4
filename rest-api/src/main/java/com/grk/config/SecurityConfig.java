package com.grk.config;

import com.grk.rest.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by grk on 2/7/14.
 */
@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

//    @Autowired
//    private AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;

    @Bean(name = "grkAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("letsnosh").password("noshing").roles("USER");
    }

    @Bean(name = "grkUserDetailsService")
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/img/**", "/js/**");
    }

    protected void configure(HttpSecurity http) throws Exception {

        LOG.info("http.toString(): " + http.toString());

        http
//                .requestMatchers()
//                    .antMatchers("/user/authenticate")
//                    .and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/players").hasRole("USER")
                    .antMatchers(HttpMethod.POST, "/teams").hasRole("USER")
                    .antMatchers(HttpMethod.PUT, "/players/*").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/teams/*").hasRole("USER");
//                  .and()
//              .httpBasic().realmName("glenn");

        http.addFilterAfter(new AuthenticationTokenProcessingFilter(userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable();
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/**").hasRole("USER")
//                .anyRequest().anonymous().and().httpBasic();
//    }

    class AuthenticationTokenProcessingFilter extends GenericFilterBean {
        private final UserDetailsService userService;

        AuthenticationTokenProcessingFilter(UserDetailsService userService) {
            this.userService = userService;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            LOG.debug("AuthenticationTokenProcessingFilter::doFilter()");

            Assert.isNull(SecurityContextHolder.getContext().getAuthentication());

            if (!(request instanceof HttpServletRequest)) {
                throw new RuntimeException("Expecting a HTTP request");
            }

            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String authToken = httpRequest.getHeader("X-Auth-Token");
            LOG.debug(".getHeader(\"X-Auth-Token\") = {}", authToken);

            if (authToken != null) {
                String username = TokenUtils.getUsernameFromToken(authToken);

                if (username != null) {
                    UserDetails userDetails = this.userService.loadUserByUsername(username);
                    if (TokenUtils.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(httpRequest));

                        LOG.debug("AuthenticationTokenProcessingFilter::setting context: {}", authentication.getPrincipal());

                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        Assert.notNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                    }
                }
            }

            chain.doFilter(request, response);
        }

    }

}
