package com.example.timetable.converter;

import com.example.timetable.dto.LecturerDTO;
import com.example.timetable.entity.Lecturer;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class LecturerConverter {


    public Lecturer convertDTOtoEntity(LecturerDTO lecturerDTO){
        Lecturer lecturer = new Lecturer();
        lecturer.setEmail(lecturerDTO.getEmail());
        lecturer.setPassword(lecturerDTO.getPassword());
        return lecturer;

    }

    public LecturerDTO convertEntityToDTO(Lecturer lecturer){
        LecturerDTO lecturerDTO = new LecturerDTO();
        lecturerDTO.setName(lecturer.getName());
        lecturerDTO.setEmail(lecturer.getEmail());
        return lecturerDTO;
    }

    //Get all list of lecturers with subjects




}
