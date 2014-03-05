package com.grk.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.grk.core.repository.PlayerRepository;
import com.grk.core.repository.TeamRepository;
import com.grk.core.services.PlayerService;
import com.grk.core.services.PlayerServiceImpl;
import com.grk.core.services.TeamService;
import com.grk.core.services.TeamServiceImpl;

@Configuration
public class ServiceConfig {

	private static Logger LOG = LoggerFactory.getLogger(ServiceConfig.class);
	
	@Bean
	public PlayerService createPlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
		LOG.debug("createPlayerService(PlayerRepository, TeamRepository)");
		return new PlayerServiceImpl(playerRepository, teamRepository);
	}
	
	@Bean
	public TeamService createTeamService(TeamRepository teamRepository) {
		LOG.debug("createTeamService(TeamRepository)");
        return new TeamServiceImpl(teamRepository);

	}
}
