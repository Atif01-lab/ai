package com.example.timetable.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Name;

    private String email;
    private String password;

    private String token;

    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;

}
