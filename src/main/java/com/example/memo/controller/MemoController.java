package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController // 항상 JSON 형태로 통신하기 위해
@RequestMapping("/memos")// prefix하는 URL 설정 시 사용
public class MemoController {
    private final Map<Long, Memo> memoList = new HashMap<>();

    // 실제로 사용할  Controller API
    // 응답 타입: MemoResponseDto
    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto dto) {
        // 식별자가 1씩 증가하도록 만듦
        long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContent());

        // Inmemory DB(현재 프로젝트 내에서 만든 자료구조인 memoList)에 Memo 저장
        memoList.put(memoId, memo);

        return new MemoResponseDto(memo);
    }

    @GetMapping("/{id}")
    public MemoResponseDto findMemoById(@PathVariable Long id) {

        Memo memo = memoList.get(id);

        return new MemoResponseDto(memo);
    }

    @PutMapping("/{id}")
    public MemoResponseDto updateMemoById(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ) {
        Memo memo = memoList.get(id);
        memo.update(dto);

        return new MemoResponseDto(memo);
    }
}
