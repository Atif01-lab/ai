package com.example.timetable.service;


import com.example.timetable.TimetableExceptionHandler;
import com.example.timetable.dto.ClassDTO;
import com.example.timetable.dto.StudentDTO;
import com.example.timetable.entity.*;
import com.example.timetable.entity.Class;
import com.example.timetable.exception.ClassroomNotAvailableException;
import com.example.timetable.repository.ClassRepository;
import com.example.timetable.repository.ClassroomRepository;
import com.example.timetable.repository.LecturerRepository;
import com.example.timetable.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.timetable.converter.ClassConverter;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    ClassRepository classRepository;
    @Autowired
    LecturerRepository lecturerRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ClassConverter classConverter;

    @Autowired
    TimetableExceptionHandler timetableExceptionHandler;



    @Transactional
    public Optional<Class> assignClassToLecturer(Long id, Long lecturerId) {
        Optional<Class> optionalClass = classRepository.findById(id);
        Optional<Lecturer> optionalLecturer = lecturerRepository.findById(lecturerId);
        if (optionalClass.isPresent() && optionalLecturer.isPresent()) {
            Class clazz = optionalClass.get();
            Lecturer lecturer = optionalLecturer.get();
            clazz.setLecturer(lecturer);
            classRepository.save(clazz);
            return Optional.of(clazz);
        }
        return Optional.empty();
    }


    public List<ClassDTO> getClassesForLecturer(Long lecturerId) {
        List<Class> classes = classRepository.findAllByLecturerId(lecturerId);

        //creating a new list that will contain all properties as DTO

        List<ClassDTO> classList = new ArrayList<>();
        for (Class clazz : classes) {
            if (clazz.getIsCancelled() == false) {
                ClassDTO classDTO = classConverter.toDTO(clazz);
                classList.add(classDTO);
            }

        }
        return classList;
    }

    public ResponseEntity<?> cancelClass(Long lecturerId, Long classId) {
        List<Class> classList = classRepository.findAllByLecturerId(lecturerId);
        for (Class clazz : classList) {
            if (clazz.getId().equals(classId)) {
                clazz.setIsCancelled(true);
                classRepository.save(clazz);
                return ResponseEntity.ok("Class cancelled successfully ");
            } else {
                return ResponseEntity.badRequest().body("Class not found");
            }
        }
        return ResponseEntity.notFound().build();
    }


    //create a new class
    public ClassDTO createClass(ClassDTO classDTO) {
        Class clazz = classConverter.toEntity(classDTO);
        String date = classDTO.getDate();
        String time = classDTO.getTime();

        //find all classes by classroom id

        List<Class> classes = classRepository.findAllByClassroom_Id(classDTO.getClassroomId());
        for (Class c : classes) {
            if (c.getDate().equals(date) && c.getTime().equals(time)) {
                throw new ClassroomNotAvailableException("Classroom not available");
            }

        }
        classRepository.save(clazz);
        return classConverter.toDTO(clazz);
    }





    //find all classes for a student
    public List<ClassDTO> getAllClassesForStudent(Long studentId) {
        List<Class> classes = classRepository.findAllClassesByStudentId(studentId);
        List<ClassDTO> classList = new ArrayList<>();
        for (Class clazz : classes) {
            if (clazz.getIsCancelled() == false) {
                ClassDTO dto = classConverter.toDTO(clazz);
                classList.add(dto);
            }
        }
        return classList;
    }


    //add a student to a class
    @Transactional
    public Class enrollStudentToClass(Long classId, Long studentId) {
        Class clazz = classRepository.findById(classId).get();
        Student student = studentRepository.findById(studentId).get();

        if (clazz.getEnrolledStudents().size() < clazz.getClassroom().getCapacity()) {
            clazz.getEnrolledStudents().add(student);
            classRepository.save(clazz);
            return clazz;
        } else {
            throw new ClassroomNotAvailableException("Classroom is full");
        }
    }


    //remove a student from a class
    @Transactional
    public Class removeStudentFromClass(Long classId, Long studentId) {
        Class clazz = classRepository.findById(classId).get();
        Student student = studentRepository.findById(studentId).get();

        if(clazz.getEnrolledStudents().contains(student)){
            clazz.getEnrolledStudents().remove(student);
            classRepository.save(clazz);
            return clazz;
           }else{
            throw new ClassroomNotAvailableException("Student is not enrolled in this class");
        }

}

    public boolean checkLecturer(String classId, Long id) {
        Class clazz = classRepository.findById(Long.parseLong(classId)).get();
        Lecturer lecturer = lecturerRepository.findById(id).get();
        if (clazz.getLecturer().getId().equals(lecturer.getId())) {
            return true;
        } else {
            return false;
        }
    }
}







