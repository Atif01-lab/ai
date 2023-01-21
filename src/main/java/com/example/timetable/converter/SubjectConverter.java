package com.example.timetable.converter;


import com.example.timetable.dto.SubjectDTO;
import com.example.timetable.entity.Subject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class SubjectConverter {

    //Get all list of subjects with lecturers

    public SubjectDTO convertEntityToDTO(Subject subject){
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(subject.getName());
        subjectDTO.setLecturer(subject.getLecturer().getName());
        return subjectDTO;
    }



}
