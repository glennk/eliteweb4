package com.grk.core.services;


import com.grk.core.event.*;
import org.junit.Before;
import org.junit.Test;

import com.grk.core.domain.Player;
import com.grk.core.domain.Team;
import com.grk.core.repository.PlayerRepository;
import com.grk.core.repository.TeamRepository;

import java.util.UUID;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class PlayerServiceUnitTest {

	PlayerServiceImpl serviceUnderTest;
	
	PlayerRepository mockPlayerRepo;
	TeamRepository mockTeamRepo;
	
	@Before
	public void setUp() throws Exception {
		mockPlayerRepo = mock(PlayerRepository.class);
		mockTeamRepo = mock(TeamRepository.class);
		serviceUnderTest = new PlayerServiceImpl(mockPlayerRepo, mockTeamRepo);
	}

	@Test
	public void testCreateNewPlayer() {
		when(mockPlayerRepo.save(any(Player.class))).thenReturn(new Player());
		
		CreatePlayerEvent evt = new CreatePlayerEvent(new PlayerDetails());
		
		serviceUnderTest.createPlayer(evt);
		
		verify(mockPlayerRepo).save(any(Player.class));
		verifyNoMoreInteractions(mockPlayerRepo);
	}

	@Test
	public void testCreateNewPlayerValidTeamId() {
		when(mockTeamRepo.findOne(any(String.class))).thenReturn(new Team());
		when(mockPlayerRepo.save(any(Player.class))).thenReturn(new Player());
		
		PlayerDetails playerDetails = new PlayerDetails();
		playerDetails.setTeam_id("abcdefg");
		CreatePlayerEvent evt = new CreatePlayerEvent(playerDetails);
		
		serviceUnderTest.createPlayer(evt);
		verify(mockPlayerRepo).save(any(Player.class));
		verifyNoMoreInteractions(mockPlayerRepo);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateNewPlayerInvalidTeamId() {
		when(mockTeamRepo.findOne(any(String.class))).thenReturn(null);
		
		PlayerDetails playerDetails = new PlayerDetails();
		playerDetails.setTeam_id("abcdefg");
		CreatePlayerEvent evt = new CreatePlayerEvent(playerDetails);
		
		serviceUnderTest.createPlayer(evt);
	}
    
    @Test
    public void removeAnPlayerFromTheSystemFailsIfNotPermitted() {
        String key = UUID.randomUUID().toString();

        Player player = new Player() {
            @Override
            public boolean canBeDeleted() {
                return false;
            }
        };

        when(mockPlayerRepo.findOne(key)).thenReturn(player);

        DeletePlayerEvent ev = new DeletePlayerEvent(key);

        PlayerDeletedEvent playerDeletedEvent = serviceUnderTest.deletePlayer(ev);

        verify(mockPlayerRepo, never()).delete(ev.getKey());

        assertTrue(playerDeletedEvent.isEntityFound());
        assertFalse(playerDeletedEvent.isDeletionCompleted());
    }

    @Test
    public void removeAnPlayerFromTheSystemFailsIfNotPresent() {
        String key = UUID.randomUUID().toString();

        when(mockPlayerRepo.findOne(key)).thenReturn(null);
        DeletePlayerEvent ev = new DeletePlayerEvent(key);

        PlayerDeletedEvent orderDeletedEvent = serviceUnderTest.deletePlayer(ev);

        verify(mockPlayerRepo, never()).delete(ev.getKey());

        assertFalse(orderDeletedEvent.isEntityFound());
        assertFalse(orderDeletedEvent.isDeletionCompleted());

    }

    @Test
    public void removeAnPlayerFromTheSystemWorksIfExists() {

        String key = UUID.randomUUID().toString();
        Player player = new Player();

        when(mockPlayerRepo.findOne(key)).thenReturn(player);

        DeletePlayerEvent ev = new DeletePlayerEvent(key);

        PlayerDeletedEvent orderDeletedEvent = serviceUnderTest.deletePlayer(ev);

        verify(mockPlayerRepo).delete(ev.getKey());

        assertTrue(orderDeletedEvent.isEntityFound());
        assertTrue(orderDeletedEvent.isDeletionCompleted());
    }

    @Test
    public void updateAnPlayerFromTheSystemFailsIfNotPresent() {
        String key = UUID.randomUUID().toString();

        when(mockPlayerRepo.findOne(key)).thenReturn(null);
    }

    @Test
    public void updateAnPlayerFromTheSystemWorksIfExists() {

        String key = UUID.randomUUID().toString();
        Player player = new Player();

        when(mockPlayerRepo.findOne(key)).thenReturn(player);
        when(mockPlayerRepo.save(any(Player.class))).thenReturn(player);

        PlayerUpdateEvent ev = new PlayerUpdateEvent(key, new PlayerDetails());

        PlayerUpdatedEvent playerUpdatedEvent = serviceUnderTest.updatePlayer(ev);

        verify(mockPlayerRepo).save(any(Player.class));

        assertTrue(playerUpdatedEvent.isEntityFound());
        assertTrue(playerUpdatedEvent.isUpdateCompleted());
    }

    @Test
    public void updateAnPlayerFromTheSystemFailsIfNotPermitted() {
        String key = UUID.randomUUID().toString();

        Player player = new Player() {
            @Override
            public boolean canBeUpdated() {
                return false;
            }
        };

        when(mockPlayerRepo.findOne(key)).thenReturn(player);

        PlayerDetails details = new PlayerDetails();
        PlayerUpdateEvent ev = new PlayerUpdateEvent(key, details);

        PlayerUpdatedEvent playerUpdatedEvent = serviceUnderTest.updatePlayer(ev);

        verify(mockPlayerRepo, never()).save(any(Player.class));

        assertTrue(playerUpdatedEvent.isEntityFound());

        assertFalse(playerUpdatedEvent.isUpdateCompleted());
    }
}
