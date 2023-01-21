package com.example.timetable.controller;


import com.example.timetable.dto.ClassDTO;
import com.example.timetable.dto.StudentDTO;
import com.example.timetable.entity.Student;
import com.example.timetable.repository.StudentRepository;
import com.example.timetable.service.ClassService;
import com.example.timetable.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassService classService;



    @PostMapping(path = "/login")
    public ResponseEntity<StudentDTO> login(@RequestBody StudentDTO studentDTO) {
        studentDTO = studentService.login(studentDTO.getEmail(), studentDTO.getPassword()).getBody();
        if (studentDTO != null) {
            return new ResponseEntity<>(studentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Get all classes for a student
    @GetMapping(path = "/{studentId}/classes")
    public ResponseEntity<List<ClassDTO>> getClasses(@PathVariable Long studentId) {
        List<ClassDTO> classes = classService.getAllClassesForStudent(studentId);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

}