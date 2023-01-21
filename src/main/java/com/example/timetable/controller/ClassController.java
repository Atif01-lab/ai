package com.example.timetable.controller;


import com.example.timetable.converter.ClassConverter;
import com.example.timetable.dto.StudentDTO;
import com.example.timetable.entity.Student;
import com.example.timetable.service.ClassService;
import com.example.timetable.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.timetable.entity.Class;

import com.example.timetable.dto.ClassDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ClassController {


    @Autowired
    ClassService classService;

    @Autowired
    StudentService studentService;

    @Autowired
    ClassConverter classConverter;

    public ClassController(ClassService classService, ClassConverter classConverter) {
        this.classService = classService;
        this.classConverter = classConverter;
    }

//Assign a class to a lecturer
    @PutMapping("/admin/assign/{classId}/{lecturerId}")//assign class to lecturer
    public ResponseEntity<ClassDTO> assignClassToLecturer(@PathVariable Long classId, @PathVariable Long lecturerId) {
        Optional<Class> optionalClass = classService.assignClassToLecturer(classId, lecturerId);
        if (optionalClass.isPresent()) {
            Class clazz = optionalClass.get();
            ClassDTO classDTO = classConverter.toDTO(clazz);
            return ResponseEntity.ok(classDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //Get all classes for a lecturer
    @GetMapping("/lecturer/{lecturerId}")
    public ResponseEntity<List<ClassDTO>> getClasses(@PathVariable Long lecturerId) {
        List<ClassDTO> classes = classService.getClassesForLecturer(lecturerId);

        if (classes.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(classes);
        }
    }

    //cancel class
    @PatchMapping("/lecturer/{lecturerId}/{classId}")
    public ResponseEntity<?> cancelClass(@PathVariable Long lecturerId, @PathVariable Long classId) {
        return classService.cancelClass(lecturerId,classId);
    }


    //Get enrolled students in a class
    @GetMapping("/admin/class/{classId}/students")
    public ResponseEntity<List<StudentDTO>> getEnrolledStudentsByClassId(@PathVariable Long classId) {
        List<StudentDTO> enrolledStudents = studentService.getEnrolledStudentsByClassId(classId);
        return ResponseEntity.ok(enrolledStudents);
    }




    //Get enrolled student in a subject
    @GetMapping("/admin/subject/{subjectId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsBySubject(@PathVariable Long subjectId) {
        List<StudentDTO> students = studentService.getStudentsBySubject(subjectId);
        return ResponseEntity.ok(students);
}
    //Do not change the above code


    //Create a new class
    @PostMapping("/admin/class/create")

    public ResponseEntity<ClassDTO> createClass(@RequestBody ClassDTO classDTO) {
        //get date and time from RequestBody

        ClassDTO newClass = classService.createClass(classDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClass);
    }


    //Remove student from a class
    @DeleteMapping("/admin/remove/{classId}/students/{studentId}")
    public String removeStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        Optional<Class> optionalClass = Optional.ofNullable(classService.removeStudentFromClass(classId, studentId));
        if (optionalClass.isPresent()) {
            return "Student removed from class successfully";
        }
        return "Student not removed from class";
    }

    //Enroll student to class
    @PostMapping("/admin/enrol/{classId}/students/{studentId}")
    public String enrollStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        Optional<Class> optionalClass = Optional.ofNullable(classService.enrollStudentToClass(classId, studentId));
        if (optionalClass.isPresent()) {
            return "Student enrolled to class successfully";
        }
        return "Student not enrolled to class";
    }



}


