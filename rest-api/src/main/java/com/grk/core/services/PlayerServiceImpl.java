package com.grk.core.services;

import java.util.ArrayList;
import java.util.List;

import com.grk.core.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.grk.core.domain.Player;
import com.grk.core.domain.Team;
import com.grk.core.repository.PlayerRepository;
import com.grk.core.repository.TeamRepository;

public class PlayerServiceImpl implements PlayerService {

	private static Logger LOG = LoggerFactory
			.getLogger(PlayerServiceImpl.class);

	private PlayerRepository playerRepo;
	private TeamRepository teamRepo;

	public PlayerServiceImpl(PlayerRepository playerRepository,
			TeamRepository teamRepository) {
		this.playerRepo = playerRepository;
		this.teamRepo = teamRepository;
		LOG.info("PlayerServiceImpl({}, {})", playerRepository.getClass(),
				teamRepository.getClass());
	}

	@Override
	public PlayerDetailsEvent getPlayer(RequestPlayerDetailsEvent evt) {
		LOG.debug("getPlayer(id={})", evt.getId());
		Player p = playerRepo.findOne(evt.getId());
		return new PlayerDetailsEvent(p.getId(), p.toPlayerDetails());
	}

	@Override
	public AllPlayersEvent getAllPlayers(RequestAllPlayersEvent evt) {
		LOG.debug("getAllPlayers()");
		List<PlayerDetails> listOfPlayersDetails = new ArrayList<PlayerDetails>();
		for (Player p : playerRepo.findAll()) {
			listOfPlayersDetails.add(p.toPlayerDetails());
		}
		return new AllPlayersEvent(listOfPlayersDetails);
	}

	@Override
	public PlayerCreatedEvent createPlayer(CreatePlayerEvent evt) {
		PlayerDetails playerDetails = evt.getPlayerDetails();
		LOG.debug("createPlayer() from details: {}", playerDetails);
		Player p = Player.fromPlayerDetails(playerDetails);

		// if team_id is provided, validate it exists
		if (playerDetails.getTeam_id() != null) {
			LOG.debug("createPlayer(), team_id: {} provided, validating ...",
					playerDetails.getTeam_id());
			Team t = teamRepo.findOne(playerDetails.getTeam_id());
			if (t == null) {
				LOG.debug("team_id: {} does not exist in datastore; throwing IllegalArgumentException", playerDetails.getTeam_id());
				throw new IllegalArgumentException(
						"Specified Team ID does not exist in datastore!");
			}
			p.setTeam_id(t.getId());
		}

		Player result = playerRepo.save(p);
		LOG.debug("player successfully created, id: {}", result.getId());
		
		PlayerCreatedEvent playerCreatedEvent = new PlayerCreatedEvent(
				result.getId(), result.toPlayerDetails());
		return playerCreatedEvent;
	}
    
    @Override
    public PlayerDeletedEvent deletePlayer(DeletePlayerEvent deletePlayerEvent) {

        Player player = playerRepo.findOne(deletePlayerEvent.getKey());

        if (player == null) {
            return PlayerDeletedEvent.notFound(deletePlayerEvent.getKey());
        }

        PlayerDetails details = player.toPlayerDetails();

        //TODOCUMENT This contains some specific domain logic, not exposed to the outside world, and not part of the
        //persistence rules.

        if (!player.canBeDeleted()) {
            return PlayerDeletedEvent.deletionForbidden(deletePlayerEvent.getKey(), details);
        }

        playerRepo.delete(deletePlayerEvent.getKey());
        return new PlayerDeletedEvent(deletePlayerEvent.getKey(), details);
    }

    @Override
    public PlayerUpdatedEvent updatePlayer(PlayerUpdateEvent updatePlayerEvent) {

        Player player = playerRepo.findOne(updatePlayerEvent.getId());

        if (player == null) {
            return PlayerUpdatedEvent.notFound(updatePlayerEvent.getId());
        }

        PlayerDetails details = updatePlayerEvent.getDetails();

        player.updateFromDetails(details);

        if (!player.canBeUpdated()) {
            return PlayerUpdatedEvent.updateForbidden(updatePlayerEvent.getId(), details);
        }

        Player updatedPlayer = playerRepo.save(player);
        return new PlayerUpdatedEvent(updatePlayerEvent.getId(), updatedPlayer.toPlayerDetails());
    }

}
