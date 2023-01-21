package com.example.timetable.service;

import com.example.timetable.converter.LecturerConverter;
import com.example.timetable.dto.LecturerDTO;
import com.example.timetable.entity.Lecturer;
import com.example.timetable.repository.ClassRepository;
import com.example.timetable.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LecturerService {


    @Autowired
    LecturerRepository lecturerRepository;

    @Autowired
    ClassRepository classRepository;
    @Autowired
    LecturerConverter lecturerConverter;


    public LecturerDTO login(String email, String password) {
        LecturerDTO lecturerDTO = null;
        if(lecturerRepository.findByEmailAndPassword(email, password) != null){
            lecturerDTO = lecturerConverter.convertEntityToDTO(lecturerRepository.findByEmailAndPassword(email, password));
        }else{

        }
        return lecturerDTO;

    } //Login

    //check token
    public Lecturer checkToken(String token) {
        {
            Lecturer lecturer = lecturerRepository.findByToken(token);
            if (lecturer != null && lecturer.getToken() != null)
            {
                return lecturer;
            }

            return null;
        }




    }}



