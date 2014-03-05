package com.grk.rest.controller;

import com.grk.rest.TokenUtils;
import com.grk.rest.domain.User;
import com.grk.rest.domain.UserTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by grk on 2/27/14.
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("grkUserDetailsService")
    private UserDetailsService userService;

    @Autowired
    @Qualifier("grkAuthenticationManager")
    private AuthenticationManager authManager;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = "application/json")
    public UserTransfer authenticate(@RequestBody User user) {
        LOG.debug("authenticate(username: {}, password: {})", user.getUsername(), user.getPassword());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = user.getUsername(); //"letsnosh";
        String password = user.getPassword(); //"noshing";

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = this.authManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Boolean> roles = new HashMap<>();

        UserDetails userDetails = this.userService.loadUserByUsername(username);

        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.put(authority.toString(), Boolean.TRUE);
        }

        UserTransfer authenticatedUser = new UserTransfer();
        authenticatedUser.setUsername(user.getUsername());
        authenticatedUser.setToken(TokenUtils.createToken(user.getUsername())); //UUID.randomUUID().toString());
        authenticatedUser.setRoles(roles);
        return authenticatedUser;
    }
}
