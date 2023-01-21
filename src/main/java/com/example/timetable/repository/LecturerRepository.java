package com.example.timetable.repository;


import com.example.timetable.entity.Department;
import com.example.timetable.entity.Lecturer;
import com.example.timetable.entity.Student;
import com.example.timetable.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    Lecturer findByEmailAndPassword(String email, String password);


    //find by id
    Optional<Lecturer> findById(Long id);

    //find by token
    Lecturer findByToken(String token); //check token




    //Get all lecturers







}
