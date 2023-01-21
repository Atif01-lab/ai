package com.example.timetable.service;


import com.example.timetable.converter.AdminConverter;
import com.example.timetable.dto.AdminDTO;
import com.example.timetable.dto.StudentDTO;
import com.example.timetable.entity.Admin;
import com.example.timetable.entity.Student;
import com.example.timetable.repository.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AdminService {


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminConverter adminConverter;

    //Constructor for testing
    public AdminService(AdminRepository adminMockRepository) {
           this.adminRepository = adminMockRepository;

    }


    //Login
    public ResponseEntity<AdminDTO> login(String email, String password) {
        AdminDTO adminDTO = null;
        Optional<Admin> admin = Optional.ofNullable(adminRepository.findByEmailAndPassword(email, password));
        if (admin.isPresent()) {
            adminDTO = adminConverter.convertEntityToDTO(admin.get());
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(adminDTO, HttpStatus.OK);
    }


    //check token
    public Admin checkToken(String token) {

            Admin admin = adminRepository.findByToken(token);
            if (admin != null && admin.getToken() != null) {
                return admin;
            }

            return null;

    }
}
