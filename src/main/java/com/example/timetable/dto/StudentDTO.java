package com.example.timetable.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class StudentDTO {
    private Long id;
    private String name;

    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private String token;

    private Long classId;

    private Long majorId;

    private Long subjectId;


}
