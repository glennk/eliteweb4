package com.grk.rest.controller;

import com.grk.core.event.*;
import com.grk.core.services.TeamService;
import com.grk.rest.domain.TeamSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teams")
public class TeamController {

	private static Logger LOG = LoggerFactory.getLogger(TeamController.class);

	@Autowired
	private TeamService service;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<TeamSummary> getAllTeams() {
		LOG.debug("GET /teams");
		LOG.debug("getAllTeams(); with service: " + service.getClass());
		AllTeamsEvent evt = service.getAllTeams(new RequestAllTeamsEvent());
		LOG.debug("# of teams: " + evt.getTeamDetails().size());
		List<TeamSummary> lts = new ArrayList<TeamSummary>();
		for (TeamDetails teamDetails : evt.getTeamDetails()) {
			lts.add(TeamSummary.fromTeamDetails(teamDetails));
		}
		
		return lts;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<TeamSummary> team(@PathVariable String id) {
		LOG.debug("GET /teams/{} with service: {}", id, service.getClass());
		TeamDetailsEvent tde = service.getTeam(new RequestTeamDetailsEvent(id));
		
		if (tde.getTeamDetails() != null) {
			TeamSummary ts = TeamSummary.fromTeamDetails(tde.getTeamDetails());
			return new ResponseEntity<TeamSummary>(ts, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<TeamSummary>(HttpStatus.NOT_FOUND);
		}
	}

    /**
     * TODO - since we have a unique index on name + age, and we set the
     * default MongoClient write concern to WriteConcern.SAFE, this method
     * will throw a InternalServerError since the service will throw an
     * uncaught Exception. The question is should we catch the server
     * error and translate it into a more meaningful error (like Forbidden)
     *
     * @param team
     * @param builder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TeamSummary> createTeam(@RequestBody TeamSummary team, UriComponentsBuilder builder) {
        LOG.debug("POST /teams/{json payload converted to TeamSummary: {}} with service: {}", team.toString(), service.getClass());
        TeamCreatedEvent teamCreated = service.createTeam(new CreateTeamEvent(team.toTeamDetails()));
		
		TeamSummary newTeam = TeamSummary.fromTeamDetails(teamCreated.getTeamDetails());
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/teams/{id}")
				.buildAndExpand(teamCreated.getNewTeamKey())
				.toUri());
		
		return new ResponseEntity<TeamSummary>(newTeam, headers, HttpStatus.CREATED);
	}

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<TeamSummary> deleteTeam(@PathVariable String id) {
        LOG.debug("DELETE /teams/{}", id);

        TeamDeletedEvent teamDeleted = service.deleteTeam(new DeleteTeamEvent(id));

        if (!teamDeleted.isEntityFound()) {
            return new ResponseEntity<TeamSummary>(HttpStatus.NOT_FOUND);
        }

        TeamSummary team = TeamSummary.fromTeamDetails(teamDeleted.getDetails());

        if (teamDeleted.isDeletionCompleted()) {
            return new ResponseEntity<TeamSummary>(team, HttpStatus.OK);
        }

        return new ResponseEntity<TeamSummary>(team, HttpStatus.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<TeamSummary> updateTeam(@PathVariable String id, @RequestBody TeamSummary team) {
        LOG.debug("PUT /teams/{}", id);

        TeamUpdatedEvent teamUpdated = service.updateTeam(new TeamUpdateEvent(id, team.toTeamDetails()));

        if (!teamUpdated.isEntityFound()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TeamSummary updatedTeam = TeamSummary.fromTeamDetails(teamUpdated.getDetails());

        if (teamUpdated.isUpdateCompleted()) {
            return new ResponseEntity<TeamSummary>(team, HttpStatus.OK);
        }

        return new ResponseEntity<>(updatedTeam, HttpStatus.FORBIDDEN);
    }
}
