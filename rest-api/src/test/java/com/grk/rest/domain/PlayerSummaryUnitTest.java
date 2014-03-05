package com.grk.rest.domain;

import com.grk.core.event.PlayerDetails;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class PlayerSummaryUnitTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		PlayerSummary ps = new PlayerSummary();
		ps.setId("id");
		ps.setFirstname("firstname");
		ps.setLastname("lastname");
		ps.setEmail("email");
		ps.setPhone("phone");
		ps.setJerseyNum("jerseyNum");
		ps.setTeam_id("team_id");
		PlayerDetails pd = ps.toPlayerDetails();
		Map<String, String> v1 = BeanUtils.describe(ps);
		v1.remove("class");
		System.out.println("v1 := " + v1);
//		System.out.println("v1a := " + v1a);
		Map<String, String> v2 = BeanUtils.describe(pd);
		v2.remove("class");
		System.out.println("v2 := " + v2);
		boolean r = v1.equals(v2);
		assertTrue(r);
//BeanComparator c = new BeanComparator();
//System.out.println("a: = " + c.compare(ps, pd));
//		assertTrue(ps.equals(pd));
	}

}
