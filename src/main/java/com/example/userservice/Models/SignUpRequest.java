package com.example.userservice.Models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
    private String name;

    public User mapToUser(){
        User newUser = new User();
        newUser.setUsername(this.getUsername());
        newUser.setPassword(this.getPassword());
        newUser.setName(this.getName());
        newUser.setUserRoles(UserRoles.USER);
        return newUser;
    }
}
