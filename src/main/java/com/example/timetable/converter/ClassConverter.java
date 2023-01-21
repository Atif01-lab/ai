package com.example.timetable.converter;

import com.example.timetable.dto.ClassDTO;
import com.example.timetable.entity.Class;

import com.example.timetable.entity.Classroom;
import com.example.timetable.entity.Department;
import com.example.timetable.entity.Lecturer;
import org.springframework.stereotype.Component;

@Component
public class ClassConverter {

    public ClassDTO toDTO(Class clazz) {
        ClassDTO dto = new ClassDTO();
        dto.setId(clazz.getId());
        dto.setTime(clazz.getTime());
        dto.setDate(clazz.getDate());
        dto.setDepartmentId(clazz.getDepartment().getId());
        dto.setClassroomId(clazz.getClassroom().getId());
        dto.setLecturerId(clazz.getLecturer().getId());
        dto.setIsCancelled(clazz.getIsCancelled());
        return dto;
    }

    public Class toEntity(ClassDTO dto) {
        Class clazz = new Class();
        clazz.setId(dto.getId());
        clazz.setTime(dto.getTime());
        clazz.setDate(dto.getDate());
        Department department = new Department();
        department.setId(dto.getDepartmentId());
        clazz.setDepartment(department);
        Classroom classroom = new Classroom();
        classroom.setId(dto.getClassroomId());
        clazz.setClassroom(classroom);
        Lecturer lecturer = new Lecturer();
        lecturer.setId(dto.getLecturerId());
        clazz.setLecturer(lecturer);
        clazz.setIsCancelled(dto.getIsCancelled());
        return clazz;
    }
}


