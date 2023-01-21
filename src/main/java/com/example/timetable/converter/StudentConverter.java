package com.example.timetable.converter;
import com.example.timetable.dto.StudentDTO;
import com.example.timetable.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentConverter {



    public Student convertDTOtoEntity(StudentDTO studentDTO){
        Student student = new Student();
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());
        return student;

    }

    public StudentDTO convertEntityToDTO(Student student){
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        return studentDTO;
    }
}
