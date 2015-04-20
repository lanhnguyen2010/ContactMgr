package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import vn.kms.launch.contactmgr.ContactMgrApp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
@WebAppConfiguration
public class ContactControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    /**
     * Test action get a contact by id (GET:/api/contacts{id})
     *
     * @throws Exception
     */
    @Test
    public void testGetContact() throws Exception {
        // Get an existing contact
        this.mockMvc
            .perform(
                get("/api/contacts/1")
                    .accept(MediaType
                        .parseMediaType("application/json;charset=UTF-8")))
            .andExpect(status().isOk());

        // Get a contact which doesn't exist
        this.mockMvc
            .perform(
                get("/api/contacts/-1")
                    .accept(MediaType
                        .parseMediaType("application/json;charset=UTF-8")))
            .andExpect(status().isNotFound());
    }
}
