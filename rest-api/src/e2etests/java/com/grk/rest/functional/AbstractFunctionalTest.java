package com.grk.rest.functional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;

import java.util.Arrays;

/**
 * Created by grk on 2/9/14.
 */
public abstract class AbstractFunctionalTest {

    static HttpHeaders getHeaders(String auth) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        byte[] encodedAuthorization = Base64.encode(auth.getBytes());
        headers.add("Authorization", "Basic " + new String(encodedAuthorization));

        return headers;
    }

}
