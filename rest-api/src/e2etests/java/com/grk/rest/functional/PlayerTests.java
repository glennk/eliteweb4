package com.grk.rest.functional;

import com.grk.rest.controller.fixture.RestDataFixture;
import com.grk.rest.domain.PlayerSummary;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.*;


public class PlayerTests extends AbstractFunctionalTest {

    @Test
    public void thatPlayersCanBeAddedAndQueried() {

        // Copy from SecurityConfig.java
        HttpEntity<String> requestEntity = new HttpEntity<>(
                RestDataFixture.standardPlayerJSON(), getHeaders("letsnosh" + ":" + "noshing"));

        RestTemplate template = new RestTemplate();
        ResponseEntity<PlayerSummary> entity = template.postForEntity(
                "http://localhost:8080/players", requestEntity,
                PlayerSummary.class);

        String path = entity.getHeaders().getLocation().getPath();

        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertTrue(path.startsWith("/players/"));
        PlayerSummary player = entity.getBody();

        System.out.println("The Player ID is: " + player.getId());
        System.out.println("The Location of resource is: " + entity.getHeaders().getLocation());

    }

    @Test
    public void thatPlayersCannotBeAddedAndQueriedWithBadUser() {

        HttpEntity<String> requestEntity = new HttpEntity<>(
                RestDataFixture.standardPlayerJSON(),
                getHeaders("letsnosh" + ":" + "BADPASSWORD"));

        RestTemplate template = new RestTemplate();
        try {
            ResponseEntity<PlayerSummary> entity = template.postForEntity(
                    "http://localhost:8080/players",
                    requestEntity, PlayerSummary.class);

            fail("Request Passed incorrectly with status " + entity.getStatusCode());
        } catch (HttpClientErrorException ex) {
            assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        }
    }

}
