package com.example.timetable.controller;


import com.example.timetable.dto.ClassDTO;
import com.example.timetable.dto.LecturerDTO;
import com.example.timetable.exception.ClassroomNotAvailableException;
import com.example.timetable.repository.LecturerRepository;
import com.example.timetable.service.ClassService;
import com.example.timetable.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/lecturer")
public class LecturerController {
    @Autowired
    private LecturerService lecturerService;
    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    ClassService classService;

    @PostMapping(path = "/login")
    public ResponseEntity<LecturerDTO> login(@RequestBody LecturerDTO lecturerDTO) {
        lecturerDTO = lecturerService.login(lecturerDTO.getEmail(), lecturerDTO.getPassword());

        if (lecturerDTO != null) {
            return new ResponseEntity<>(lecturerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } //login

    //Get all classes for a lecturer
    @GetMapping(path = "/classes/{lecturerId}")
    public ResponseEntity<List<ClassDTO>> getClasses(@PathVariable Long lecturerId) {
        List<ClassDTO> classes = classService.getClassesForLecturer(lecturerId);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

}



