package com.example.timetable.converter;

import com.example.timetable.dto.AdminDTO
        ;
import com.example.timetable.entity.Admin;
import com.example.timetable.entity.Student;
import org.springframework.stereotype.Component;


@Component
public class AdminConverter {


    public Admin convertDTOtoEntity(AdminDTO adminDTO){
        Admin admin = new Admin();
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        return admin;

    }

    public AdminDTO convertEntityToDTO(Admin admin){
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setEmail(admin.getEmail());
        return adminDTO;
    }
}
