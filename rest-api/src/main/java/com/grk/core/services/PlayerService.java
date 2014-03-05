package com.grk.core.services;

import com.grk.core.event.*;

public interface PlayerService {

	public PlayerDetailsEvent getPlayer(RequestPlayerDetailsEvent evt);
	
	public AllPlayersEvent getAllPlayers(RequestAllPlayersEvent evt);
	
	public PlayerCreatedEvent createPlayer(CreatePlayerEvent evt);

    public PlayerDeletedEvent deletePlayer(DeletePlayerEvent evt);

    public PlayerUpdatedEvent updatePlayer(PlayerUpdateEvent evt);
}

