package com.grk.rest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * Created by grk on 3/2/14.
 */
public class TokenUtils {

    static String _token = "039d82b8";

    public static String createToken(String username) {
        Assert.notNull(username);
        StringBuilder builder = new StringBuilder();
        builder.append(username);
        builder.append(":");
        builder.append(_token);
        return builder.toString();
    }

    public static String getUsernameFromToken(String token) {
        Assert.notNull(token);
        String[] parts = token.split(":");
        return parts[0];
    }

    public static boolean validateToken(String authToken, UserDetails userDetails) {
        String[] parts = authToken.split(":");

        return  _token.equals(parts[1]);
    }
}
