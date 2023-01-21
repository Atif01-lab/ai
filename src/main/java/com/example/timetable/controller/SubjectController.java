package com.example.timetable.controller;


import com.example.timetable.dto.SubjectDTO;
import com.example.timetable.entity.Lecturer;
import com.example.timetable.entity.Subject;
import com.example.timetable.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @Autowired


    //Get all list of subjects with lecturers

    @GetMapping(path = "/admin/subjects/all")
    public ResponseEntity<List<SubjectDTO>> getAllSubjects(){
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
 //assign subject to a lecturer
@PutMapping("/admin/{subjectId}/{lecturerId}")
    public ResponseEntity<SubjectDTO> updateLecturer(@PathVariable Long subjectId, @PathVariable Long lecturerId) {
        SubjectDTO subjectDTO = subjectService.updateLecturer(subjectId, lecturerId);
        if (subjectDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(subjectDTO);
        }
    }
}
