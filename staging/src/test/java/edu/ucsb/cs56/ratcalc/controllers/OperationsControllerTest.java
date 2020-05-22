package edu.ucsb.cs56.ratcalc.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import autograder.GradescopeTestClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import com.github.tkutcher.jgrade.gradedtest.GradedTest;
import org.junit.Rule;
import org.junit.rules.Timeout;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@GradescopeTestClass
public class OperationsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Test
    @GradedTest(name="getAddPage_ContentType() ", points=17.6)
    public void getAddPage_ContentType() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    @GradedTest(name="getAddPage_hasNum1Input()", points=17.6)
    public void getAddPage_hasNum1Input() throws Exception {
        String elemXPath = "//input[@type='number' and @name='num1']";
        mvc.perform(MockMvcRequestBuilders.get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andExpect(xpath(elemXPath).exists());
    }

    @Test
    @GradedTest(name="getAddPage_hasNum2Input() ", points=17.6)
    public void getAddPage_hasNum2Input() throws Exception {
        String elemXPath = "//input[@type='number' and @name='num2']";
        mvc.perform(MockMvcRequestBuilders.get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andExpect(xpath(elemXPath).exists());
    }

    @Test
    @GradedTest(name="getAddPage_hasDenom1Input() ", points=4)
    public void getAddPage_hasDenom1Input() throws Exception {
        String elemXPath = "//input[@type='number' and @name='denom1']";
        mvc.perform(MockMvcRequestBuilders.get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andExpect(xpath(elemXPath).exists());
    }

    @Test
    @GradedTest(name="getAddPage_hasDenom2Input() ", points=5)
    public void getAddPage_hasDenom2Input() throws Exception {
        String elemXPath = "//input[@type='number' and @name='denom2']";
        mvc.perform(MockMvcRequestBuilders.get("/add").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk()).andExpect(xpath(elemXPath).exists());
    }

}
