package com.example.timetable.controller;

import com.example.timetable.dto.AdminDTO;
import com.example.timetable.repository.AdminRepository;
import com.example.timetable.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AdminControllerTest {


    @Autowired
    private AdminController adminController;



    @Test
    public void testLogin() throws Exception {
        // Create a sample adminDTO
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setEmail("admin@university.edu");
        adminDTO.setPassword("password123");


        // Perform the login method
        ResponseEntity<AdminDTO> response = adminController.login(adminDTO);

        // Assert that the response is successful
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getEmail(), adminDTO.getEmail());


    }

    @Test
    public void testLoginFail() throws Exception {
        // Create a sample adminDTO
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setEmail("admin@university.edu");
        adminDTO.setPassword("password12");

        // Perform the login method
        ResponseEntity<AdminDTO> response = adminController.login(adminDTO);

        // Assert that the response is successful
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }
}