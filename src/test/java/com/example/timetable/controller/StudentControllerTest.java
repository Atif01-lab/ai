package com.example.timetable.controller;


import com.example.timetable.dto.AdminDTO;
import com.example.timetable.dto.StudentDTO;
import com.example.timetable.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class StudentControllerTest {


    @Autowired
    StudentController studentController;




    @Test
    public void testLogin() throws Exception {
        // Create a sample studentDTO

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setEmail("jane.doe@university.edu");
        studentDTO.setPassword("student123");

        // Perform the login method
        ResponseEntity<StudentDTO> response = studentController.login(studentDTO);

        // Assert that the response is successful
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getEmail(), studentDTO.getEmail());
    }

    @Test
    public void testLoginFail() throws Exception {
        // Create a sample studentDTO
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setEmail("jane.doe@university.edu" );
        studentDTO.setPassword("student12");

        // Perform the login method
        ResponseEntity<StudentDTO> response = studentController.login(studentDTO);

        // Assert that the response is successful
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);


    }


}
