package com.fastcampus.loan.service;

import com.fastcampus.loan.domain.Counsel;
import com.fastcampus.loan.dto.CounselDTO;
import com.fastcampus.loan.exception.BaseException;
import com.fastcampus.loan.exception.ResultType;
import com.fastcampus.loan.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CounselService {
    private final CounselRepository counselRepository;
    private final ModelMapper modelMapper;

    // 대출 상담 등록
    @Transactional
    public CounselDTO.Response create(CounselDTO.Request request) {
        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.setAppliedAt(LocalDateTime.now());

        Counsel created = counselRepository.save(counsel);

        return modelMapper.map(created, CounselDTO.Response.class);
    }

    // 대출 상담 조회
    @Transactional(readOnly = true)
    public CounselDTO.Response get(Long counselId) {
        Counsel counsel = counselRepository.findById(counselId)
                .orElseThrow(() -> {
                    throw new BaseException(ResultType.SYSTEM_ERROR);
                });

        return modelMapper.map(counsel, CounselDTO.Response.class);
    }

    // 대출 상담 수정
    @Transactional
    public CounselDTO.Response update(Long counselId, CounselDTO.Request request) {
        Counsel counsel = counselRepository.findById(counselId)
                .orElseThrow(() -> {
                    throw new BaseException(ResultType.SYSTEM_ERROR);
                });

        // 수정
        counsel.setName(request.getName());
        counsel.setCellPhone(request.getCellPhone());
        counsel.setEmail(request.getEmail());
        counsel.setMemo(request.getMemo());
        counsel.setAddress(request.getAddress());
        counsel.setAddressDetail(request.getAddressDetail());
        counsel.setZipCode(request.getZipCode());

        counselRepository.save(counsel);

        return modelMapper.map(counsel, CounselDTO.Response.class);
    }

    // 대출 상담 삭제
    @Transactional
    public void delete(Long counselId) {
        Counsel counsel = counselRepository.findById(counselId)
                .orElseThrow(() -> {
                    throw new BaseException(ResultType.SYSTEM_ERROR);
                });

        // soft delete
        counsel.setIsDeleted(true);

        counselRepository.save(counsel);
    }
}