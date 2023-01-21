package com.example.timetable.repository;


import com.example.timetable.entity.Major;
import com.example.timetable.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmailAndPassword(String email, String password); //login

    @Query("SELECT c.enrolledStudents FROM Class c WHERE c.id = :classId")
    List<Student> findEnrolledStudentsByClassId(@Param("classId") Long classId);

    @Query("SELECT c.enrolledStudents FROM Class c WHERE c.id = :subjectId")
    List<Student> findStudentsBySubject(@Param("subjectId") Long subjectId);

    Student findByToken(String token); //check token

}
