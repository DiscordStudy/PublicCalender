package discordstudy.calender.global.exception;

import lombok.Getter;

/**
 * 여러 라이브러리에서는 RuntimeException 을 상속한 Exception 을 발생시킴
 * RuntimeException 으로 예외를 처리하게 되면 프로젝트 내 예외와 라이브러리 예외의
 * 구분이 어려워 질 수 있음<br>
 * 이를 구분하기 위해 프로젝트 내 예외를 던지기 위한 클래스를 만들어 사용함
 */
@Getter
public class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;

    private ApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApplicationException of(ErrorCode errorCode) {
        return new ApplicationException(errorCode);
    }
}
