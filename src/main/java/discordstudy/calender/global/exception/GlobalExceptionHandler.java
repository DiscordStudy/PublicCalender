package discordstudy.calender.global.exception;

import discordstudy.calender.global.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<Void>> applicationExceptionHandler(ApplicationException exception) {
        return ApiResponse.error(exception.getErrorCode());
    }

    /**
     * Validation 예외를 핸들링 하기 위한 메소드
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Void>> bindExceptionHandler(BindException exception) {
        return ApiResponse.errorCustom(
                HttpStatus.BAD_REQUEST,
                exception.getAllErrors().get(0).getDefaultMessage()
        );
    }

    /**
     * DB 예외를 핸들링 하기 위한 메소드<br>
     * 현재 로그를 적재하진 않고 있음 추후 로그를 적재하기 위한 수단을 준비해야 함
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> dbException(DataAccessException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.errorCustom(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "DB Error"
        );
    }


    /**
     * 프로젝트에서 직접 발생시키지 않은 예외들을 처리하기 위한 메소드<br>
     * 현재 로그를 적재하진 않고 있음 추후 로그를 적재하기 위한 수단을 준비해야 함
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> serverException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.errorCustom(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Server Error"
        );
    }
}
