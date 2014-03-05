package com.grk.rest.controller;

import java.util.ArrayList;
import java.util.List;

import com.grk.core.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.grk.core.services.PlayerService;
import com.grk.rest.domain.PlayerSummary;

@Controller
@RequestMapping("/players")
public class PlayerController {

	private static Logger LOG = LoggerFactory.getLogger(PlayerController.class);

	@Autowired
	private PlayerService service;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<PlayerSummary> getAllPlayers() {
		LOG.info("GET /players");
		LOG.info("getAllPlayers() with service: " + service.getClass());
		AllPlayersEvent players = service
				.getAllPlayers(new RequestAllPlayersEvent());
		LOG.info("# of players: " + players.getPlayerDetails().size());
		List<PlayerSummary> lps = new ArrayList<PlayerSummary>();
		for (PlayerDetails player : players.getPlayerDetails()) {
			lps.add(PlayerSummary.fromPlayerDetails(player));
		}

		return lps;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<PlayerSummary> player(@PathVariable String id) {
		LOG.info("GET /players/{id}");
		LOG.debug("player(" + id + "), with service: " + service.getClass());
		PlayerDetailsEvent pde = service
				.getPlayer(new RequestPlayerDetailsEvent(id)); // repository.findOne(id);
		LOG.info("p = " + pde.getPlayerDetails());

		if (pde.getPlayerDetails() != null) {
			PlayerSummary pr = PlayerSummary.fromPlayerDetails(pde
					.getPlayerDetails());
			return new ResponseEntity<PlayerSummary>(pr, HttpStatus.OK);
		} else {
			return new ResponseEntity<PlayerSummary>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PlayerSummary> createPlayer(
			@RequestBody PlayerSummary player, UriComponentsBuilder builder) {
		LOG.info("POST /player/{json payload}");
		LOG.info("createPlayer(json: " + player + ")");
		PlayerCreatedEvent playerCreated = service
				.createPlayer(new CreatePlayerEvent(player.toPlayerDetails()));

		PlayerSummary newPlayer = PlayerSummary.fromPlayerDetails(playerCreated
				.getPlayerDetails());

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(builder.path("/players/{id}")
				.buildAndExpand(playerCreated.getNewPlayerKey())
				.toUri());

		return new ResponseEntity<PlayerSummary>(newPlayer, headers,
				HttpStatus.CREATED);
	}

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<PlayerSummary> deletePlayer(@PathVariable String id) {
        LOG.debug("DELETE /players/{}", id);

        PlayerDeletedEvent playerDeleted = service.deletePlayer(new DeletePlayerEvent(id));

        if (!playerDeleted.isEntityFound()) {
            return new ResponseEntity<PlayerSummary>(HttpStatus.NOT_FOUND);
        }

        PlayerSummary player = PlayerSummary.fromPlayerDetails(playerDeleted.getDetails());

        if (playerDeleted.isDeletionCompleted()) {
            return new ResponseEntity<PlayerSummary>(player, HttpStatus.OK);
        }

        return new ResponseEntity<PlayerSummary>(player, HttpStatus.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<PlayerSummary> updatePlayer(@PathVariable String id, @RequestBody PlayerSummary player) {
        LOG.debug("PUT /players/{}", id);

        PlayerUpdatedEvent playerUpdated = service.updatePlayer(new PlayerUpdateEvent(id, player.toPlayerDetails()));

        if (!playerUpdated.isEntityFound()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PlayerSummary updatedPlayer = PlayerSummary.fromPlayerDetails(playerUpdated.getDetails());

        if (playerUpdated.isUpdateCompleted()) {
            return new ResponseEntity<PlayerSummary>(player, HttpStatus.OK);
        }

        return new ResponseEntity<>(updatedPlayer, HttpStatus.FORBIDDEN);
    }

}
