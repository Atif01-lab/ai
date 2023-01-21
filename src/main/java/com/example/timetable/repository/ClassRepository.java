package com.example.timetable.repository;


import com.example.timetable.entity.Class;
import com.example.timetable.entity.Classroom;
import com.example.timetable.entity.Lecturer;
import com.example.timetable.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    Optional<Class> findById(Long id);
    List<Class> findByEnrolledStudents_Id(Long studentId);

    //@Query("SELECT c FROM Class c WHERE c.lecturer= :lecturer")
    List<Class> findAllByLecturerId(Long lecturerId);


    //find all classes by classroom

    List<Class> findAllByClassroom_Id(Long classroomId);


    //find all classes by student

//Quesy for finding all classes by enrolled students
    @Query("SELECT c FROM Class c JOIN c.enrolledStudents s WHERE s.id = :studentId")
    List<Class> findAllClassesByStudentId(@Param("studentId") Long studentId);





}