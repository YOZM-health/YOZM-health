package com.example.yozmhealth.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USER") // 매핑할 테이블 이름 명시
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO") // 테이블의 USER_NO와 매핑
    private Long id;

    @Column(name = "USER_ID", nullable = false, length = 50) // 사용자 아이디와 매핑
    private String username;

    @Column(name = "USER_PW", nullable = false, length = 250) // 사용자 비밀번호와 매핑
    private String password;

    @Column(name = "AGE", nullable = false) // 사용자 나이와 매핑
    private int age;

    @Column(name = "ROLES", nullable = false, length = 100) // 사용자 역할과 매핑
    private String roles;

    @Column(name = "USER_EMAIL", length = 50) // 이메일 주소
    private String email;

    @Column(name = "USER_NAME", nullable = false, length = 50) // 사용자 이름
    private String name;

    @Column(name = "USER_BIRTH", nullable = false, length = 100) // 생년월일
    private String birth;

    @Column(name = "USER_TEL", nullable = false, length = 100) // 전화번호
    private String phone;

    @Column(name = "USER_ADDR", length = 300) // 주소
    private String address;

    @Column(name = "USER_NICKNAME", nullable = false, length = 50) // 닉네임
    private String nickname;

    @Column(name = "ENROLL_DATE", nullable = false) // 가입일
    private LocalDateTime enrollDate = LocalDateTime.now();

    @Column(name = "PROFILE_IMG", length = 300) // 프로필 이미지
    private String profileImage;

    @Column(name = "USER_DEL_FL", nullable = false, length = 1) // 탈퇴 여부
    private String delFlag = "N";

    @Column(name = "USER_TYPE", nullable = false) // 회원 유형
    private Integer userType = 1;

    @Column(name = "USER_CENTER", length = 1000) // 활동 센터
    private String userCenter;
}

