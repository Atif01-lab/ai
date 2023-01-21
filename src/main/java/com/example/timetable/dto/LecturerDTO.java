package com.example.timetable.dto;


import com.example.timetable.entity.Subject;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class LecturerDTO {

    private Long id;
    private String name;


    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private List <Subject> subjects;


}
