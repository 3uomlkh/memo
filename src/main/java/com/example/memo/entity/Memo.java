package com.example.memo.entity;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // 클래스의 모든 필드에 대한 getter 생성
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자 생성
public class Memo {
    private Long id;
    private String title;
    private String content;

    public void update(MemoRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
}
