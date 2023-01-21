package com.example.timetable.controller;

import com.example.timetable.dto.SubjectDTO;
import com.example.timetable.service.SubjectService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class SubjectControllerTest {
    @InjectMocks
    private SubjectController subjectController;

    @Mock
    private SubjectService subjectService;

    @Test
    public void testGetAllSubjects() throws Exception {
        // Create a sample list of SubjectDTOs
        List<SubjectDTO> subjectDTOList = new ArrayList<>();
        SubjectDTO subject1 = new SubjectDTO();
        subject1.setName("Math");
        subjectDTOList.add(subject1);
        SubjectDTO subject2 = new SubjectDTO();
        subject2.setName("Science");
        subjectDTOList.add(subject2);

        // Mock the subjectService.getAllSubjects() method to return the sample list of SubjectDTOs
        when(subjectService.getAllSubjects()).thenReturn(subjectDTOList);

        // Perform the getAllSubjects method
        ResponseEntity<List<SubjectDTO>> response = subjectController.getAllSubjects();

        // Assert that the response is successful and contains the sample list of SubjectDTOs
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), subjectDTOList);
    }


    @Test
        public void testUpdateLecturer() throws Exception {
            // Create a sample SubjectDTO
            SubjectDTO subjectDTO = new SubjectDTO();
            subjectDTO.setName("Math");
            subjectDTO.setLecturer("John");
            Long subjectId = 1L;
            Long lecturerId = 2L;

            // Mock the subjectService.updateLecturer(subjectId, lecturerId) method to return the sample SubjectDTO
            when(subjectService.updateLecturer(subjectId, lecturerId)).thenReturn(subjectDTO);

            // Perform the updateLecturer method
            ResponseEntity<SubjectDTO> response = subjectController.updateLecturer(subjectId, lecturerId);

            // Assert that the response is successful and contains the updated SubjectDTO
            assertEquals(response.getStatusCode(), HttpStatus.OK);
            assertEquals(response.getBody(), subjectDTO);
            assertEquals(response.getBody().getLecturer(), subjectDTO.getLecturer());
        }

        @Test
        public void testUpdateLecturer_notFound() throws Exception {
            Long subjectId = 1L;
            Long lecturer = 2L;

            // Mock the subjectService.updateLecturer(subjectId, lecturerId) method to return null
            when(subjectService.updateLecturer(subjectId, lecturer)).thenReturn(null);

            // Perform the updateLecturer method
            ResponseEntity<SubjectDTO> response = subjectController.updateLecturer(subjectId, lecturer);

            // Assert that the response is not found
            assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }



