package com.example.hanghaeblogprac.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)        // User Type(user, admin) Enum 연결
    private UserRoleEnum role;


    public User(String username, String password, UserRoleEnum role) {
        this.username = username;           // 사용자 아이디
        this.password = password;           // 사용자 패스워드
        this.role = role;                   // 사용자 Type(role)
    }

}