package com.grk.core.services;

import com.grk.core.domain.Team;
import com.grk.core.event.*;
import com.grk.core.repository.TeamRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class TeamServiceUnitTest {

    TeamServiceImpl serviceUnderTest;

    TeamRepository mockTeamRepo;

    @Before
    public void setUp() throws Exception {
        mockTeamRepo = mock(TeamRepository.class);
        serviceUnderTest = new TeamServiceImpl(mockTeamRepo);
    }

    @Test
    public void testCreateNewTeam() {
        when(mockTeamRepo.save(any(Team.class))).thenReturn(new Team());

        CreateTeamEvent evt = new CreateTeamEvent(new TeamDetails());

        serviceUnderTest.createTeam(evt);

        verify(mockTeamRepo).save(any(Team.class));
        verifyNoMoreInteractions(mockTeamRepo);
    }

    @Test
    public void removeAnTeamFromTheSystemFailsIfNotPresent() {
        String key = UUID.randomUUID().toString();

        when(mockTeamRepo.findOne(key)).thenReturn(null);
    }

    @Test
    public void removeAnTeamFromTheSystemFailsIfNotPermitted() {
        String key = UUID.randomUUID().toString();

        Team team = new Team() {
            @Override
            public boolean canBeDeleted() {
                return false;
            }
        };

        when(mockTeamRepo.findOne(key)).thenReturn(team);

        DeleteTeamEvent ev = new DeleteTeamEvent(key);

        TeamDeletedEvent teamDeletedEvent = serviceUnderTest.deleteTeam(ev);

        verify(mockTeamRepo, never()).delete(ev.getKey());

        assertTrue(teamDeletedEvent.isEntityFound());
        assertFalse(teamDeletedEvent.isDeletionCompleted());
    }

    @Test
    public void removeAnTeamFromTheSystemWorksIfExists() {

        String key = UUID.randomUUID().toString();
        Team team = new Team();

        when(mockTeamRepo.findOne(key)).thenReturn(team);

        DeleteTeamEvent ev = new DeleteTeamEvent(key);
        TeamDeletedEvent orderDeletedEvent = serviceUnderTest.deleteTeam(ev);

        verify(mockTeamRepo).delete(ev.getKey());

        assertTrue(orderDeletedEvent.isEntityFound());
        assertTrue(orderDeletedEvent.isDeletionCompleted());
    }

    @Test
    public void updateAnTeamFromTheSystemFailsIfNotPresent() {
        String key = UUID.randomUUID().toString();

        when(mockTeamRepo.findOne(key)).thenReturn(null);

        DeleteTeamEvent ev = new DeleteTeamEvent(key);
        TeamDeletedEvent orderDeletedEvent = serviceUnderTest.deleteTeam(ev);

        verify(mockTeamRepo, never()).delete(ev.getKey());

        assertFalse(orderDeletedEvent.isEntityFound());
        assertFalse(orderDeletedEvent.isDeletionCompleted());
    }

    @Test
    public void updateAnTeamFromTheSystemWorksIfExists() {

        String key = UUID.randomUUID().toString();
        Team team = new Team();

        when(mockTeamRepo.findOne(key)).thenReturn(team);
        when(mockTeamRepo.save(any(Team.class))).thenReturn(team);

        TeamUpdateEvent ev = new TeamUpdateEvent(key, new TeamDetails());

        TeamUpdatedEvent teamUpdatedEvent = serviceUnderTest.updateTeam(ev);

        verify(mockTeamRepo).save(any(Team.class));

        assertTrue(teamUpdatedEvent.isEntityFound());
        assertTrue(teamUpdatedEvent.isUpdateCompleted());
    }

    @Test
    public void updateAnTeamFromTheSystemFailsIfNotPermitted() {
        String key = UUID.randomUUID().toString();

        Team team = new Team() {
            @Override
            public boolean canBeUpdated() {
                return false;
            }
        };

        when(mockTeamRepo.findOne(key)).thenReturn(team);

        TeamDetails details = new TeamDetails();
        TeamUpdateEvent ev = new TeamUpdateEvent(key, details);

        TeamUpdatedEvent teamUpdatedEvent = serviceUnderTest.updateTeam(ev);

        verify(mockTeamRepo, never()).save(any(Team.class));

        assertTrue(teamUpdatedEvent.isEntityFound());

        assertFalse(teamUpdatedEvent.isUpdateCompleted());
    }

//	@Test(expected=IllegalArgumentException.class)
//	public void testCreateNewPlayerInvalidTeamId() {
//		when(mockTeamRepo.findOne(any(String.class))).thenReturn(null);
//		
//		PlayerDetails playerDetails = new PlayerDetails();
//		playerDetails.setTeam_id("abcdefg");
//		CreatePlayerEvent evt = new CreatePlayerEvent(playerDetails);
//		
//		serviceUnderTest.createPlayer(evt);
//	}
}
