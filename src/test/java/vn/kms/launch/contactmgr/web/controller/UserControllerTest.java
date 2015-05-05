package vn.kms.launch.contactmgr.web.controller;

import com.fasterxml.jackson.core.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import scala.util.parsing.json.JSON;
import vn.kms.launch.contactmgr.ContactMgrApp;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Test forgetPassword function (PUT:/api/users/reset_password?email={email})
     *
     * @throws Exception
     */
    @Test
    public void testForgetPassword() throws Exception {
        JSONObject exception = new JSONObject();
        // Send to an email not existed
        exception.put("message", "The email is not existed");
        this.mockMvc
            .perform(
                put("/api/users/reset_password?email=xyzabc@gmail.com")
                    .accept(MediaType
                        .parseMediaType("application/json;charset=UTF-8")))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(exception.toString()));

        // Send to an email existed
        exception.put("message", "The new password was sent to your email");
        this.mockMvc
            .perform(
                put("/api/users/reset_password?email=odthientho@gmail.com")
                    .accept(MediaType
                        .parseMediaType("application/json;charset=UTF-8")))
            .andExpect(status().isOk())
            .andExpect(content().string(exception.toString()));
    }
}
