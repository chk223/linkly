package com.example.linkly.entity;

import com.example.linkly.grade.UserGrade;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.annotations.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
//@SoftDelete // 기능 -> 밑에 있는 @SQLDelete(~~~) + @Where(~~~)
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?") // 엔티티 삭제 시, delted 컬럼 값을 true로 변경
@Where(clause = "deleted = false")
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    @Column(length = 10)
    private String name;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    @Column(length = 255)
    private String password;

    // 프로필 사진
    @Column
    private String profileImg;

    // 프로필 소개
    @Column(length = 50)
    private String profileIntro;

    // 프로필 링크
    @Column
    private String profileUrl;

    // 유저 등급
    @Enumerated(EnumType.STRING)  // Enum을 String으로 저장
//    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'BASIC'")
    @Column(length = 10)
    private UserGrade grade = UserGrade.BASIC;

    public User() {
    }

    public User(String email, String password, String name, String profileImg, String profileIntro, String profileUrl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.profileImg = profileImg;
        this.profileIntro = profileIntro;
        this.profileUrl = profileUrl;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public void updateProfileIntro(String profileIntro) {
        this.profileIntro = profileIntro;
    }

    public void updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    // 등급 전환 메서드 (엔티티 클래스 내부에서 처리)
    public void updateGrade() {
        this.grade = (this.grade == UserGrade.BASIC) ? UserGrade.VIP : UserGrade.BASIC;
    }

}