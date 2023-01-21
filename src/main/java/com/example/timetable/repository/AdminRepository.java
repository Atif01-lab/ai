package com.example.timetable.repository;


import com.example.timetable.entity.Admin;
import com.example.timetable.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmailAndPassword(String email, String password);


    @Query("SELECT a FROM Admin a WHERE a.token = :token")
    Admin findByToken(String token);

}