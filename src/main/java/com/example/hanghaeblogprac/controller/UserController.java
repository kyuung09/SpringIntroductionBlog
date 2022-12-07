package com.example.hanghaeblogprac.controller;

import com.example.hanghaeblogprac.dto.LoginRequestDto;
import com.example.hanghaeblogprac.dto.ResponseDto;
import com.example.hanghaeblogprac.dto.SignupRequestDto;
import com.example.hanghaeblogprac.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController                             // @Controller 어노테이션은 html을 반환하기 때문에 RestController를 사용함
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    // User Signup
    @ResponseBody
    @PostMapping("/signup")
    public ResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        try {
            return userService.signup(signupRequestDto);
        } catch (Exception e) {
            return new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());                         // 예외 발생시 에러 내용, Httpstatus(400)을 리턴값으로 전달한다.
        }
    }

    // User Signup
    @ResponseBody
    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            return userService.login(loginRequestDto, response);
        } catch (Exception e) {
            return new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());                         // 예외 발생시 에러 내용, Httpstatus(400)을 리턴값으로 전달한다.
        }
    }
}