package com.grk.rest.domain;

import com.grk.core.event.PlayerDetails;
import com.grk.rest.controller.fixture.RestDataFixture;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class PlayerTests {

    private Logger LOG = LoggerFactory.getLogger(PlayerTests.class);

    @Test
    public void thatPlayerDetailsCanConvertToPlayerSummary() {
        PlayerDetails p = RestDataFixture.standardPlayer();

        PlayerSummary ps = PlayerSummary.fromPlayerDetails(p);
        assertEquals(ps.getId(), p.getId());
        assertEquals(ps.getFirstname(), p.getFirstname());
        assertEquals(ps.getLastname(), p.getLastname());
        assertEquals(ps.getJerseyNum(), p.getJerseyNum());
        assertEquals(ps.getTeam_id(), p.getTeam_id());
        //TODO do more tests
    }
}
