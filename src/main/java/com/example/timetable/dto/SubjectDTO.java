package com.example.timetable.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SubjectDTO {

    private String name;
    private String lecturer;
}
