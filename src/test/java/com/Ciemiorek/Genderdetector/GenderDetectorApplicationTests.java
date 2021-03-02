package com.Ciemiorek.Genderdetector;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class GenderDetectorApplicationTests {

    @Autowired
    private MockMvc mockMvc;


    //Test check gender by all names


    @Test
    public void getGenderByAllNamesGivenOneShouldReturnHttpCode200AndFemale() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/ALL", "Kasia"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"FEMALE\""));
    }

    @Test
    public void getGenderByAllNamesGivenOneShouldReturnHttpCode200AndMale() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/ALL", "Ignacy"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"MALE\""));
    }

    @Test
    public void getGenderByAllNamesGivenTwoDifferentShouldReturnHttpCode200AndInconclusive() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/ALL", "Kasia robert"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"INCONCLUSIVE\""));

    }

    @Test
    public void getGenderByAllNamesGivenTwoFemaleShouldReturnHttpCode200AndFemale() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/ALL", "anna maria"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"FEMALE\""));

    }

    @Test
    public void getGenderByAllNamesGivenTwoMaleShouldReturnHttpCode200AnMale() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/ALL", "robert kuba"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"MALE\""));

    }

    @Test
    public void getGenderByAllNamesGivenThreeShouldReturnHttpCode200AndMale() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/ALL", "kasia robert  kuba"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"MALE\""));

    }

    @Test
    public void getGenderByAllNamesGivenThreeShouldReturnHttpCode200AndFemale() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/ALL", "robert kasia maria"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"FEMALE\""));

    }

    @Test
    public void getGenderByAllNamesGivenThreeShouldReturnHttpCode200AndInconclusive() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/ALL", "robert maria cos"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"INCONCLUSIVE\""));

    }


    //Test check gender by first name


    @Test
    public void getGenderByFirstNameGivenThreeShouldReturnHttpCode200AndMale() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/FIRST", "robert kasia maria"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"MALE\""));

    }

    @Test
    public void getGenderByFirstNameGivenThreeShouldReturnHttpCode200AndFemale() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/FIRST", "kasia robert kuba"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"FEMALE\""));

    }

    @Test
    public void getGenderByFirstNameGivenThreeShouldReturnHttpCode200AndInconclusive() throws Exception {
        mockMvc.perform(get("/gender/{fullName}/FIRST", "cos kasia maria"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"INCONCLUSIVE\""));

    }


    @Test
    public void getNamesTokensTest() throws Exception {
        mockMvc.perform(get("/gender/tokens").param("streaming", "true"))
                .andExpect(request().asyncStarted())
                .andDo(MvcResult::getAsyncResult)

                .andExpect(status().isOk())
                .andExpect(content().string(("kuba\r\ndawid\r\nrobert\r\nignacy\r\nkrzysztof\nagata\r\nalicja\r\nmaria\r\nanna\r\nkasia")));


    }


}
