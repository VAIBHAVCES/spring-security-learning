package com.example.amigossecurity.model.user;

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

//Model for storing it in security
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
// we are using small "user" because data JPA also has its own table called User
@Table
public class MyUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Makes this value as auto generated
    Integer id;

    String firstName ;

    String lastName;
    String email;
    String phone;
    String username;
    String password;


    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
//         this should be true for authentication to succeed
//         but you can change logic in future
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

