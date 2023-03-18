package com.example.jwtauthentication.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Lombok : for getters and setters for all fields and toString
@Data
// Desing pattern Builder
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name="users"
)
public class User {
    @Id
    // By default the following is strategy = GenerationType.AUTO
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
