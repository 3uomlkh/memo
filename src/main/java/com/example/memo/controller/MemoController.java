package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController // 항상 JSON 형태로 통신하기 위해
@RequestMapping("/memos")// prefix하는 URL 설정 시 사용
public class MemoController {
    private final Map<Long, Memo> memoList = new HashMap<>();

    // 실제로 사용할  Controller API
    // 응답 타입: MemoResponseDto
//    @PostMapping
//    public MemoResponseDto createMemo(@RequestBody MemoRequestDto dto) {
//        // 식별자가 1씩 증가하도록 만듦
//        long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;
//
//        // 요청받은 데이터로 Memo 객체 생성
//        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContent());
//
//        // Inmemory DB(현재 프로젝트 내에서 만든 자료구조인 memoList)에 Memo 저장
//        memoList.put(memoId, memo);
//
//        return new MemoResponseDto(memo);
//    }

    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) {
        // 식별자가 1씩 증가하도록 만듦
        long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContent());

        // Inmemory DB(현재 프로젝트 내에서 만든 자료구조인 memoList)에 Memo 저장
        memoList.put(memoId, memo);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> findAllMemos() {

        // init List
        List<MemoResponseDto> responseList = new ArrayList<>();

        // HashMap<Memo> -> List<MemoResponseDto>
        for (Memo memo : memoList.values()) {
            MemoResponseDto responseDto = new MemoResponseDto(memo);
            responseList.add(responseDto);
        }

        // Map To List - 위 코드와 동일
//        responseList = memoList.values().stream().map(MemoResponseDto::new).toList();

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }


//    @GetMapping("/{id}")
//    public MemoResponseDto findMemoById(@PathVariable Long id) {
//
//        Memo memo = memoList.get(id);
//
//        return new MemoResponseDto(memo);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id) {

        Memo memo = memoList.get(id);

        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

//    @PutMapping("/{id}")
//    public MemoResponseDto updateMemoById(
//            @PathVariable Long id,
//            @RequestBody MemoRequestDto dto
//    ) {
//        Memo memo = memoList.get(id);
//        memo.update(dto);
//
//        return new MemoResponseDto(memo);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemoById(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ) {
        Memo memo = memoList.get(id);

        // NPE 방지
        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 필수값 검증
        if (dto.getTitle() == null || dto.getContent() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 메모 수정
        memo.update(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteMemo(
            @PathVariable Long id
    ) {
        memoList.remove(id);
    }

}
