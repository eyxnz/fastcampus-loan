package com.fastcampus.loan.controller;

import com.fastcampus.loan.dto.CounselDTO;
import com.fastcampus.loan.dto.ResponseDTO;
import com.fastcampus.loan.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/counsels")
@RestController
public class CounselController extends AbstractController {
    private final CounselService counselService;

    // 대출 상담 등록
    @PostMapping
    public ResponseDTO<CounselDTO.Response> create(@RequestBody CounselDTO.Request request) {
        return ok(counselService.create(request));
    }

    // 대출 상담 조회
    @GetMapping("/{counselId}")
    public ResponseDTO<CounselDTO.Response> get(@PathVariable Long counselId) {
        return ok(counselService.get(counselId));
    }

    // 대출 상담 수정
    @PutMapping("/{counselId}")
    public ResponseDTO<CounselDTO.Response> update(
            @PathVariable Long counselId,
            @RequestBody CounselDTO.Request request
    ) {
        return ok(counselService.update(counselId, request));
    }

    // 대출 상담 삭제
    @DeleteMapping("/{counselId}")
    public ResponseDTO<Void> delete(@PathVariable Long counselId) {
        counselService.delete(counselId);

        return ok();
    }
}
