package com.grk.config;

import com.grk.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This test ensures that once everything is wired together, the wiring in the
 * WebConfig is correct and the appropriate controllers are in attendance.
 * <p/>
 * The test validates the WebConfig by mocking requests which exercise the
 * handler mappings. The full responses are also confirmed to be correct. More
 * testing could be done, but you've already asserted that your controllers
 * should work appropriately in the previous steps. This test is simply there to
 * show you that now you are configuring those components using Spring
 * JavaConfig properly.
 *
 * @author grk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Application.class})
public class WebDomainIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    /**
     * TODO - there is an inherent dependency on the 'RestDomainIntegrationTest.class' runnning first as it populates a new player entity
     *
     * @throws Exception
     */
    @Test
    public void getHome() throws Exception {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//				.build();
        mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().size(2))
                .andExpect(model().attribute("playerList", hasSize(1)))
                .andExpect(content().string(containsString("JerseyNum")));
    }
}
