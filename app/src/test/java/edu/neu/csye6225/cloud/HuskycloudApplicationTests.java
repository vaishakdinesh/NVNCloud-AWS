package edu.neu.csye6225.cloud;

import edu.neu.csye6225.cloud.configuration.DataBaseConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class HuskycloudApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/hw")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello World")));
	}

	@Test
	public void testLoginPage() throws Exception{
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void testWelcomePage() throws Exception{
		this.mockMvc.perform(get("/welcome"))
				.andDo(print()).andExpect(status().is3xxRedirection());
	}

	@Test
	public void testEmailCheck() throws Exception {
		this.mockMvc.perform(post("/email-check").with(csrf())
				.param("email","dinesh.v%40gmail.com"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("")));
	}

	@Test
	@Transactional
	public void testRegisterUser() throws Exception{
		this.mockMvc.perform(post("/register-user").with(csrf())
			.param("firstname","vaishak")
			.param("lastname","dinesh")
			.param("useremail","dinesh.v@gmail.com")
			.param("password","v"))
				.andDo(print()).andExpect(status().isOk());
	}

}
