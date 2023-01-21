package com.example.timetable.repository;

import com.example.timetable.entity.Class;
import com.example.timetable.entity.Classroom;
import com.example.timetable.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    //List<Classroom> findByDepartment(Department department);

    Optional<Classroom> findById(Long id);

    //class exists by time and date
}
