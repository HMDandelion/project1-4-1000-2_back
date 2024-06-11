package com.hmdandelion.project_1410002.common.exception.handler;

import com.hmdandelion.project_1410002.common.exception.BadRequestException;
import com.hmdandelion.project_1410002.common.exception.NoContentsException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.dto.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingController {

    /* Not Found Exception */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundException(NotFoundException e) {

        final ExceptionResponse exceptionResponse = ExceptionResponse.of(e.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(NoContentsException.class)
    public ResponseEntity<ExceptionResponse> noContentsException(NoContentsException e) {
        final ExceptionResponse res = ExceptionResponse.of(e.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(res);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestException(BadRequestException e) {
        final ExceptionResponse exceptionResponse = ExceptionResponse.of(e.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodValidException(MethodArgumentNotValidException e) {
        int code = 0;
        String message = "";

        /* 에러가 있다면 */
        if(e.getBindingResult().hasErrors()) {
            String resultCode = e.getBindingResult().getFieldError().getCode();
            switch(resultCode) {
                case "NotNull" :
                    code = 6900;
                    message = "필수 값이 누락되었습니다.";
                    break;
                case "NotBlank" :
                    code = 6901;
                    message = "필수 값이 공백으로 처리되었습니다.";
                    break;
                case "Min" :
                    code = 6902;
                    message = "알맞은 범위의 값이 입력되지 않았습니다.";
                    break;
            }
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(code, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }


}
