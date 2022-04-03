package com.example.userservice.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",unique = true,nullable = false)
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="email",unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoles userRoles = UserRoles.USER;

    @Column(name="locked")
    private Boolean locked = false;

    @Column(name="enabled")
    private Boolean enabled = false;

    public User(String username, String password, String email, String name, UserRoles roles){
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.userRoles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId())
                && getUsername().equals(user.getUsername())
                && getPassword().equals(user.getPassword())
                && getName().equals(user.getName())
                && getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(),
                getPassword(), getName(), getEmail());
    }
}
