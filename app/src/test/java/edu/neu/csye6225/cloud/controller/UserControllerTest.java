package edu.neu.csye6225.cloud.controller;

import edu.neu.csye6225.cloud.service.EmailServiceImp;
import edu.neu.csye6225.cloud.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkEmailTest() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/email-check?email=abc@gmail.com");
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse().getStatus());
        int expected = 200;
        assert(result.getResponse().getStatus() == expected);
    }

    @Test
    public void checkRegistrationTest() throws Exception{
        String data = "firstname=&lastname=dinesh&useremail=vpd@gmail.com&password=v";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/register-user").content(data);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println(result.getResponse().getStatus());
        assert(result.getResponse().getStatus() == 400);
    }

}
