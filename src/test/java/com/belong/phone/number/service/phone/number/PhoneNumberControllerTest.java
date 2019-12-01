package com.belong.phone.number.service.phone.number;

import com.belong.phone.number.service.customer.Title;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@AutoConfigureMockMvc
public class PhoneNumberControllerTest {
    
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindPhoneNumberAll() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                        .get("/phone-number/all")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    

    @Test
    public void test1FindPhoneNumberByCustomerID() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                        .get("/phone-number/CustomerId/{customerId}", 1000)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void test2FindPhoneNumberByCustomerID() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                        .get("/phone-number/CustomerId/{customerId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void test3FindPhoneNumberByCustomerID() throws Exception{
        mvc.perform(MockMvcRequestBuilders
                        .get("/phone-number/CustomerId/{customerId}", "a")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void test1ActivePhoneNumber() throws Exception{
        String inputJson = "{\"phoneNumberId\" : \"5\" , \"customerId\" : \"1\" }";
        String jsonResponse = mvc.perform(
                MockMvcRequestBuilders
                        .put("/phone-number/activate/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(inputJson)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        PhoneNumber phoneNumber = objectMapper.readValue(jsonResponse, PhoneNumber.class);
        Assert.assertEquals("0455455445", phoneNumber.getNumber());
        Assert.assertEquals(Title.Dr, phoneNumber.getCustomer().getTitle());
        Assert.assertEquals("Grape", phoneNumber.getCustomer().getName());
        Assert.assertEquals("Avocado", phoneNumber.getCustomer().getSurname());
    }
    
    @Test
    public void test4FindPhoneNumberByCustomerID() throws Exception{
        String jsonResponse = mvc.perform(MockMvcRequestBuilders
                        .get("/phone-number/CustomerId/{customerId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        PhoneNumber[] phoneNumbers = objectMapper.readValue(jsonResponse, PhoneNumber[].class);
        Assert.assertEquals("0421502121", phoneNumbers[0].getNumber());
        Assert.assertEquals("0455455445", phoneNumbers[1].getNumber());
    }
    
    @Test
    public void test2ActivePhoneNumber() throws Exception{
        String inputJson = "{\"phoneNumberId\" : \"4\" , \"customerId\" : \"1\" }";
        mvc.perform(
                MockMvcRequestBuilders
                        .put("/phone-number/activate/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)        
                        .content(inputJson)
        )
                .andExpect(MockMvcResultMatchers.status().isConflict());
                
    }
    
    @Test
    public void test3ActivePhoneNumber() throws Exception{
        String inputJson = "{\"phoneNumberId\" : \"500\" , \"customerId\" : \"1\" }";
        mvc.perform(
                MockMvcRequestBuilders
                        .put("/phone-number/activate/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(inputJson)
        )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    public void test4ActivePhoneNumber() throws Exception{
        String inputJson = "{\"phoneNumberId\" : \"8\" , \"customerId\" : \"500\" }";
        mvc.perform(
                MockMvcRequestBuilders
                        .put("/phone-number/activate/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(inputJson)
        )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    public void test5ActivePhoneNumber() throws Exception{
        String inputJson = "{\"phoneNumberId\" : \"1\" , \"customerId\" : \"1\" }";
        String jsonResponse = mvc.perform(
                MockMvcRequestBuilders
                        .put("/phone-number/activate/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(inputJson)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        PhoneNumber phoneNumber = objectMapper.readValue(jsonResponse, PhoneNumber.class);
        Assert.assertEquals("0421502121", phoneNumber.getNumber());
        Assert.assertEquals(Title.Dr, phoneNumber.getCustomer().getTitle());
        Assert.assertEquals("Grape", phoneNumber.getCustomer().getName());
        Assert.assertEquals("Avocado", phoneNumber.getCustomer().getSurname());
    }
}
