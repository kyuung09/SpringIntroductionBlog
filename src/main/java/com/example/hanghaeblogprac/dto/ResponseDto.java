package com.example.hanghaeblogprac.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto {
    private String msg;
    public ResponseDto(String msg){
        this.msg = msg;         // 수행결과(성공/실패) 리턴 메세지
    }
}
