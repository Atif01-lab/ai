package com.example.timetable.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassDTO {
    private Long id;
    private String time;
    private String date;
    private Long departmentId;
    private Long classroomId;
    private Long lecturerId;

    private List<Long> enrolledStudents;

    private Boolean isCancelled;




}

