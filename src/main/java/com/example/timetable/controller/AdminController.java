package com.example.timetable.controller;


import com.example.timetable.dto.AdminDTO;
import com.example.timetable.dto.LecturerDTO;
import com.example.timetable.dto.StudentDTO;
import com.example.timetable.entity.Admin;
import com.example.timetable.entity.Class;
import com.example.timetable.repository.AdminRepository;
import com.example.timetable.service.AdminService;
import com.example.timetable.service.ClassService;
import com.example.timetable.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ClassService classService;
    @Autowired
    AdminDTO adminDTO;

    @Autowired
    LecturerService lecturerService;




    @PostMapping(path="/login")
    public ResponseEntity<AdminDTO> login(@RequestBody AdminDTO adminDTO) {
        adminDTO = adminService.login(adminDTO.getEmail(), adminDTO.getPassword()).getBody();
        if (adminDTO != null) {
            return new ResponseEntity<>(adminDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }}


