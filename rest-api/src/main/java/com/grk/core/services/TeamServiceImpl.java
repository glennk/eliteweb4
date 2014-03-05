package com.grk.core.services;

import com.grk.core.domain.Team;
import com.grk.core.event.*;
import com.grk.core.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class TeamServiceImpl implements TeamService {

	private static Logger LOG = LoggerFactory.getLogger(TeamService.class);

//	@Autowired
	private TeamRepository teamRepo;

	public TeamServiceImpl(TeamRepository teamRepository) {
		this.teamRepo = teamRepository;
		LOG.info("TeamServiceImpl({});", teamRepository.getClass());
	}

	@Override
	public TeamDetailsEvent getTeam(RequestTeamDetailsEvent evt) {
		LOG.debug("getTeam({})", evt.getId());
		Team t = teamRepo.findOne(evt.getId());
        if (t == null) {
            return TeamDetailsEvent.notFound(evt.getId());
        }
        return new TeamDetailsEvent(t.getId(), t.toTeamDetails());
	}

	@Override
	public AllTeamsEvent getAllTeams(RequestAllTeamsEvent evt) {
		LOG.debug("getAllTeams()");
		List<TeamDetails> listOfTeamsDetails = new ArrayList<TeamDetails>();
		for (Team t : teamRepo.findAll()) {
			listOfTeamsDetails.add(t.toTeamDetails());

		}
		return new AllTeamsEvent(listOfTeamsDetails);
	}

	@Override
	public TeamCreatedEvent createTeam(CreateTeamEvent evt) {
		TeamDetails teamDetails = evt.getTeamDetails();
		LOG.debug("createTeam() from details: {}", teamDetails.toString());
		Team t = Team.fromTeamDetails(teamDetails);
		Team result = teamRepo.save(t);
		LOG.debug("team successfully created, id: {}", result.getId());
		
		TeamCreatedEvent teamCreatedEvent = new TeamCreatedEvent(
				result.getId(), result.toTeamDetails());
		return teamCreatedEvent;
	}

    @Override
    public TeamDeletedEvent deleteTeam(DeleteTeamEvent deleteTeamEvent) {
        LOG.debug("deleteTeam(id: {})", deleteTeamEvent.getKey());

        Team team = teamRepo.findOne(deleteTeamEvent.getKey());

        if (team == null) {
            return TeamDeletedEvent.notFound(deleteTeamEvent.getKey());
        }

        TeamDetails details = team.toTeamDetails();

        //TODOCUMENT This contains some specific domain logic, not exposed to the outside world, and not part of the
        //persistence rules.

        if (!team.canBeDeleted()) {
            return TeamDeletedEvent.deletionForbidden(deleteTeamEvent.getKey(), details);
        }

        teamRepo.delete(deleteTeamEvent.getKey());
        return new TeamDeletedEvent(deleteTeamEvent.getKey(), details);
    }

    @Override
    public TeamUpdatedEvent updateTeam(TeamUpdateEvent updateTeamEvent) {
        LOG.debug("updateTeam(id: {})", updateTeamEvent.getId());

        Team team = teamRepo.findOne(updateTeamEvent.getId());
        if (team == null) {
            return TeamUpdatedEvent.notFound(updateTeamEvent.getId());
        }

        TeamDetails details = updateTeamEvent.getDetails();

        team.updateFromDetails(details);

        if (!team.canBeUpdated()) {
            return TeamUpdatedEvent.updateForbidden(updateTeamEvent.getId(), details);
        }

        Team updatedTeam = teamRepo.save(team);
        return new TeamUpdatedEvent(updateTeamEvent.getId(), updatedTeam.toTeamDetails());
    }
}
