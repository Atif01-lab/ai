package com.example.timetable.service;

import com.example.timetable.converter.SubjectConverter;
import com.example.timetable.dto.SubjectDTO;
import com.example.timetable.entity.Lecturer;
import com.example.timetable.entity.Subject;
import com.example.timetable.repository.LecturerRepository;
import com.example.timetable.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SubjectConverter subjectConverter;

    @Autowired
    LecturerRepository lecturerRepository;


    //Get all list of subjects with lecturers

    public List<SubjectDTO> getAllSubjects(){
        return subjectRepository.findAll().stream().map(subjectConverter::convertEntityToDTO).collect(Collectors.toList());
    }

    //update lecturer for subject by id
    public SubjectDTO updateLecturer(Long subjectId, Long lecturerId) {
        Subject subject = subjectRepository.findById(subjectId).orElse(null);
        Lecturer lecturer = lecturerRepository.findById(lecturerId).orElse(null);

        if (subject == null || lecturer == null) {
            return null;
        } else {
            subject.setLecturer(lecturer);
            subjectRepository.save(subject);
            SubjectDTO subjectDTO = subjectConverter.convertEntityToDTO(subject);
            return subjectDTO;
        }
    }


    //Do not change the above code







}
