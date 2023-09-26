package data.entity;

import jwt.setting.config.Role;
import jwt.setting.config.SocialType;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name="user")
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    private String email;
    private String password;
    private String nickname;
    private String location;
    private String birth;
    private String phoneNumber;
    private int isGoodseul;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE
    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)
    private String refreshToken;
    //유저 권한 설정 메소드
    public void authorizeUser() { //메소드 권한 설정
        this.role = Role.USER;
    }
    //비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) { //비밀번호 암호화
        this.password = passwordEncoder.encode(this.password); 
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}