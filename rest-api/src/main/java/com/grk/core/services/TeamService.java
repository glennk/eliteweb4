package com.grk.core.services;

import com.grk.core.event.*;

public interface TeamService {

    public TeamDetailsEvent getTeam(RequestTeamDetailsEvent evt);

    public AllTeamsEvent getAllTeams(RequestAllTeamsEvent evt);

    public TeamCreatedEvent createTeam(CreateTeamEvent evt);

    public TeamDeletedEvent deleteTeam(DeleteTeamEvent evt);

    public TeamUpdatedEvent updateTeam(TeamUpdateEvent evt);

}
