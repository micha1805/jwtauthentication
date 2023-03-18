package com.example.jwtauthentication.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

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

// 'UserDetails' is an interface provided by Spring Security that
// represents the core user information that is
// stored by an authentication provider.
public class User implements UserDetails {
    @Id
    // By default the following is strategy = GenerationType.AUTO
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    // NOTE: As we use @Lombok there is a getPassword method created,
    // no need to override the one from the UserDetails interface
    private String password;

    // the enum is considered as a String
    @Enumerated(EnumType.STRING)
    private Role role;


    // The following methods come from UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


//    The following is unnecessary as it is already built thanks to the @Lombok annotation :
//
//    @Override
//    public String getPassword(){
//        return this.password;
//    }

}
