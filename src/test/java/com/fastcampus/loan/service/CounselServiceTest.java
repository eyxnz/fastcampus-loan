package com.fastcampus.loan.service;

import com.fastcampus.loan.domain.Counsel;
import com.fastcampus.loan.dto.CounselDTO;
import com.fastcampus.loan.exception.BaseException;
import com.fastcampus.loan.exception.ResultType;
import com.fastcampus.loan.repository.CounselRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CounselServiceTest {
    @InjectMocks CounselService counselService;

    @Mock private CounselRepository counselRepository;
    @Spy private ModelMapper modelMapper;

    @DisplayName("새로운 상담 Request 를 입력으로 넣으면 해당 상담 Entity 의 Response 를 반환한다.")
    @Test
    void givenRequestOfNewCounselEntity_whenRequestCounsel_thenReturnResponseOfNewCounselEntity() {
        // Given
        Counsel entity = Counsel.builder()
                .name("Member Kim")
                .cellPhone("010-1111-2222")
                .email("mail@abc.de")
                .memo("I hope to get a loan")
                .zipCode("123456")
                .address("Somewhere in Gangnam-gu, Seoul")
                .addressDetail("What Apartment No. 101, 1st floor No. 101")
                .build();

        CounselDTO.Request request = CounselDTO.Request.builder()
                .name("Member Kim")
                .cellPhone("010-1111-2222")
                .email("mail@abc.de")
                .memo("I hope to get a loan")
                .zipCode("123456")
                .address("Somewhere in Gangnam-gu, Seoul")
                .addressDetail("What Apartment No. 101, 1st floor No. 101")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class)))
                .thenReturn(entity);

        // When
        CounselDTO.Response actual = counselService.create(request);

        // Then
        assertThat(actual.getName())
                .isSameAs(entity.getName());
    }

    @DisplayName("존재하는 상담 Id 를 입력으로 넣으면 해당 상담 Entity 의 Response 를 반환한다.")
    @Test
    void givenCounselId_whenRequestExistCounselId_thenReturnResponseOfExistCounselEntity() {
        // Given
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.findById(findId))
                .thenReturn(Optional.ofNullable(entity));

        // When
        CounselDTO.Response actual = counselService.get(findId);

        // Then
        assertThat(actual.getCounselId())
                .isSameAs(findId);
    }

    @DisplayName("존재하지 않는 상담 Id 를 입력으로 넣으면 예외를 발생시킨다.")
    @Test
    void givenCounselId_whenRequestNotExistCounselId_thenThrowException() {
        // Given
        Long findId = 2L;

        when(counselRepository.findById(findId))
                .thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        // When & Then
        assertThrows(BaseException.class, () -> counselService.get(findId));
    }

    @DisplayName("존재하는 상담 Id 와 수정할 Request 를 입력으로 넣으면 수정된 Response 를 반환한다.")
    @Test
    void givenExistCounselIdAndRequestOfCounselEntity_whenRequestUpdateExistCounselInfo_thenReturnUpdateResponseOfExistCounselEntity() {
        // Given
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .name("Member Kim")
                .build();

        CounselDTO.Request request = CounselDTO.Request.builder()
                .name("Member Kang")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class)))
                .thenReturn(entity);
        when(counselRepository.findById(findId))
                .thenReturn(Optional.ofNullable(entity));

        // When
        CounselDTO.Response actual = counselService.update(findId, request);

        // Then
        assertThat(actual.getCounselId())
                .isSameAs(findId);
        assertThat(actual.getName())
                .isSameAs(request.getName());
    }
}