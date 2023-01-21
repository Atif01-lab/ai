package com.example.timetable.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class AdminDTO {


    private Long id;
    private String name;


    @Email(message = "Email is not in the correct format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private String token;
}
