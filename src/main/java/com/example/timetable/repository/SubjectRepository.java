package com.example.timetable.repository;


import com.example.timetable.entity.Lecturer;
import com.example.timetable.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {


    //Get all subjects
    List<Subject> findAll();

    //Get all subjects by lecturer
    List<Subject> findByLecturer(Lecturer lecturer);
}
