package com.example.timetable.controller;


import com.example.timetable.dto.LecturerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LecturerControllerTest {


    @Autowired
    LecturerController lecturerController;


    @Test
    public void testLogin() throws Exception {
        // Create a sample lecturerDTO
        LecturerDTO lecturerDTO = new LecturerDTO();
        lecturerDTO.setEmail("prof.john@university.edu");
        lecturerDTO.setPassword("lecturer123");


        // Perform the login method
        ResponseEntity<LecturerDTO> response = lecturerController.login(lecturerDTO);

        // Assert that the response is successful
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getEmail(), lecturerDTO.getEmail());
    }

    @Test
    public void testLoginFail() throws Exception {
        // Create a sample lecturerDTO
        LecturerDTO lecturerDTO = new LecturerDTO();
        lecturerDTO.setEmail("prof.john@university.edu");
        lecturerDTO.setPassword("lecturer12");

        // Perform the login method
        ResponseEntity<LecturerDTO> response = lecturerController.login(lecturerDTO);

        // Assert that the response is successful
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);


    }
}
