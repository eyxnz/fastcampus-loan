package com.fastcampus.loan.dto;

import com.fastcampus.loan.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
public class ResponseDTO<T> {
    private ResultObject result;

    private T data;

    public ResponseDTO(ResultObject result) {
        this.result = result;
    }

    public ResponseDTO(T data) {
        this.data = data;
    }

    public static <T> ResponseDTO<T> ok() {
        return new ResponseDTO<>(ResultObject.getSuccess());
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return new ResponseDTO<>(ResultObject.getSuccess(), data);
    }

    public static <T> ResponseDTO<T> response(T data) {
        return new ResponseDTO<>(ResultObject.getSuccess(), data);
    }

    public ResponseDTO(BaseException ex) {
        this.result = new ResultObject(ex);
    }
}