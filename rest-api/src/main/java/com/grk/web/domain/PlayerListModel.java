package com.grk.web.domain;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.grk.core.event.PlayerDetails;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PlayerListModel {

	private final Logger LOG = LoggerFactory.getLogger(PlayerListModel.class);
	
	private List<PlayerDetails> playerDetails;

	public PlayerListModel() {
		System.out.println("PlayerListModel ctor");
	}

	public PlayerListModel(List<PlayerDetails> playerDetails) {
		this.playerDetails = playerDetails;
	}

	public void setPlayerDetails(List<PlayerDetails> playerDetails) {
		this.playerDetails = Collections.unmodifiableList(playerDetails);
	}

	public List<PlayerDetails> getPlayerDetails() {
		return playerDetails;
	}
	
	public List<PlayerDetails> findAll() {
		LOG.debug("PlayerListModel.findAll()");
		return playerDetails;
	}
	
	public int getSize() {
		LOG.debug("PlayerListModel.getSize(): {}" + playerDetails.size());
		return playerDetails.size();
	}

	@Override
	public String toString() {
		return "PlayerListModel [playerDetails=" + playerDetails + "]";
	}

}
