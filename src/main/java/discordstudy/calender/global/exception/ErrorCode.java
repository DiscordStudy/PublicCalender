package discordstudy.calender.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 프로젝트에서 발생하는 예외에 대해 사전에 정의해놓은 Enum<br>
 * 필요한 에러코드가 없는 경우 직접 만들어서 사용<br>
 * 만들때 이름은 상수 & 언더스코어만 사용할것<br>
 *
 * @see <a href="https://naver.github.io/hackday-conventions-java/#constant_uppercase">코딩 컨벤션</a>
 */
@Getter
public enum ErrorCode {

    //COMMON
    NOT_FOUND(HttpStatus.NOT_FOUND, "경로가 올바르지 않습니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),

    // USER
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "아이디 비밀번호가 일치하지 않습니다."),

    // 5xx
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        httpStatus = status;
        this.message = message;
    }
}
