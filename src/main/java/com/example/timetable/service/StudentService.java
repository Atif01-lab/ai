package com.example.timetable.service;

import com.example.timetable.converter.StudentConverter;
import com.example.timetable.dto.StudentDTO;
import com.example.timetable.entity.Student;
import com.example.timetable.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor

public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentConverter studentConverter;



    //Login
    public ResponseEntity<StudentDTO> login(String email, String password) {
        StudentDTO studentDTO = null;
        Optional<Student> student = Optional.ofNullable(studentRepository.findByEmailAndPassword(email, password));
        if (student.isPresent()) {
            studentDTO = studentConverter.convertEntityToDTO(student.get());
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }


    //check token
    public Student checkToken(String token) {
        {
            Student student = studentRepository.findByToken(token);
            if (student != null && student.getToken() != null)
            {
                return student;
            }

            return null;
        }
    }


    public List<StudentDTO> getEnrolledStudentsByClassId(Long classId) {
        List<Student> students = studentRepository.findEnrolledStudentsByClassId(classId);
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for (Student student : students) {
            studentDTOList.add(studentConverter.convertEntityToDTO(student));
        }

        return studentDTOList;
    }

    public List<StudentDTO> getStudentsBySubject(Long subjectId){
        {
            List<Student> students = studentRepository.findEnrolledStudentsByClassId(subjectId);
            List<StudentDTO> studentDTOList = new ArrayList<>();
            for (Student student : students) {
                studentDTOList.add(studentConverter.convertEntityToDTO(student));
            }
            return studentDTOList;
        }
    }




}


