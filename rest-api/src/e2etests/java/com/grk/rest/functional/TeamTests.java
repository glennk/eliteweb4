package com.grk.rest.functional;

import com.grk.rest.controller.fixture.RestDataFixture;
import com.grk.rest.domain.TeamSummary;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TeamTests extends AbstractFunctionalTest {

//    @Ignore
//    @Test
//    public void thatTeamsCanBeAddedAndQueried() {
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(
//                RestDataFixture.standardTeamJSON(), getHeaders("letsnosh" + ":" + "noshing"));
//
//        RestTemplate template = new RestTemplate();
//        ResponseEntity<TeamSummary> entity = template.postForEntity(
//                "http://localhost:8080/teams", requestEntity,
//                TeamSummary.class);
//
//        String path = entity.getHeaders().getLocation().getPath();
//
//        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
//        assertTrue(path.startsWith("/teams/"));
//        TeamSummary team = entity.getBody();
//
//        System.out.println("The Team ID is: " + team.getId());
//        System.out.println("The Location of resource is: " + entity.getHeaders().getLocation());
//    }

    @Test
    public void thatTeamsCanBeAddedAndDeletedAndTheNotFound() {

        String key = "e2e-test-should-be-deleted";
        HttpEntity<String> requestEntity = new HttpEntity<>(
                RestDataFixture.customTeamJSON(key), getHeaders("letsnosh" + ":" + "noshing"));

        // Create a new Team
        System.out.println("Create new /teams resource with id: " + key);
        RestTemplate template = new RestTemplate();
        ResponseEntity<TeamSummary> entity = template.postForEntity(
                "http://localhost:8080/teams", requestEntity,
                TeamSummary.class);

        String path = entity.getHeaders().getLocation().getPath();
        System.out.println("new URI: " + entity.getHeaders().getLocation());

        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertTrue(path.startsWith("/teams/"));
        TeamSummary team = entity.getBody();
        assertEquals(key, team.getId());

        System.out.println("The Location of resource is: " + entity.getHeaders().getLocation());

        // Update the name of the resource we just created
        requestEntity = new HttpEntity<>(
                RestDataFixture.customTeamJSON(key, "a different name"), getHeaders("letsnosh" + ":" + "noshing"));
        template = new RestTemplate();
        ResponseEntity<TeamSummary> updatedEntity = template.exchange(
                entity.getHeaders().getLocation(),
                HttpMethod.PUT,
                requestEntity,
                TeamSummary.class);
        team = updatedEntity.getBody();
        assertEquals("a different name", team.getName());


        // Try to create a new resource with the same Name & Age && WriteConcern.SAFE this
        // throws a InternalServerError - TODO - might it be better to catch the Server error
        // and translate it into a HttpStatus.FORBIDDEN or something like that??
        System.out.println("Try to create duplicate (name + age unique constraint) /teams resource");
        requestEntity = new HttpEntity<>(
                RestDataFixture.customTeamJSON("a-different-key", "a different name"), getHeaders("letsnosh" + ":" + "noshing"));

        try {
            ResponseEntity<TeamSummary> shouldNotCreate = template.postForEntity(
                    "http://localhost:8080/teams", requestEntity,
                    TeamSummary.class);
            fail("should not make it here");
        } catch (HttpServerErrorException ex) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatusCode());
        }

        // Delete the /teams/{id} resource we just created
        System.out.println("Delete resource at Location: " + entity.getHeaders().getLocation());
        template = new RestTemplate();
        ResponseEntity<TeamSummary> deletedEntity = template.exchange(
                entity.getHeaders().getLocation(),
                HttpMethod.DELETE,
                null,
                TeamSummary.class);
        assertEquals(HttpStatus.OK, deletedEntity.getStatusCode());


        // Query for the deleted Team and validate we get a Not Found 403 Response
        System.out.println("Verify resource no longer exists at Location: " + entity.getHeaders().getLocation());
        template = new RestTemplate();
        try {
            ResponseEntity<TeamSummary> entityNotFound = template.getForEntity(entity.getHeaders().getLocation(),
                    TeamSummary.class);

            fail("Request Passed incorrectly with status " + entityNotFound.getStatusCode());
        } catch (HttpClientErrorException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        }
    }

    @Test
    public void thatPlayersCannotBeAddedAndQueriedWithBadUser() {

        HttpEntity<String> requestEntity = new HttpEntity<>(
                RestDataFixture.standardTeamJSON(),
                getHeaders("letsnosh" + ":" + "BADPASSWORD"));

        RestTemplate template = new RestTemplate();
        try {
            ResponseEntity<TeamSummary> entity = template.postForEntity(
                    "http://localhost:8080/players",
                    requestEntity, TeamSummary.class);

            fail("Request Passed incorrectly with status " + entity.getStatusCode());
        } catch (HttpClientErrorException ex) {
            TestCase.assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        }
    }


}
