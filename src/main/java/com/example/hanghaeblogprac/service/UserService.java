package com.example.hanghaeblogprac.service;

import com.example.hanghaeblogprac.dto.LoginRequestDto;
import com.example.hanghaeblogprac.dto.ResponseDto;
import com.example.hanghaeblogprac.dto.SignupRequestDto;
import com.example.hanghaeblogprac.entity.User;
import com.example.hanghaeblogprac.entity.UserRoleEnum;
import com.example.hanghaeblogprac.jwt.JwtUtil;
import com.example.hanghaeblogprac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    // Create User Function

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";     // 비교 대상 ADMIN_TOKEN 값


    // Create User Function
    @Transactional
    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();                               // ID
        String password = signupRequestDto.getPassword();                               // PW


        Optional<User> found = userRepository.findByUsername(username);                 // ID 중복 확인
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");                 // 중복된 사용자가 있으면 Exception
        }


        UserRoleEnum role = UserRoleEnum.USER;                                          // 사용자 권한 확인
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);                                                      // DB에 유저 정보 저장
        return new ResponseDto("success", HttpStatus.OK.value());                  // DB에 정상적으로 저장 되었을 경우 결과 리턴
    }

    // Login User
    @Transactional(readOnly = true)
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // Check User
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 회원을 찾을 수 없습니다.")
        );

        // Check Password
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // Create Token
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        System.out.println(response);
        return new ResponseDto("success", HttpStatus.OK.value());
    }
}