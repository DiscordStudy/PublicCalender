package discordstudy.calender.global.dto;

import discordstudy.calender.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * 공통 응답을 위한 객체
 * <p>
 * ResponseEntity 내부에 제네릭으로 사용
 * <pre>
 * {@code
 * public ResponseEntity<ApiResponse<Object>> apiMethod(){
 *     //Do Somethings....
 *     return ApiResponse.ok();
 *  }
 * }
 */
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    /**
     * Http 상태 코드를 표시
     */
    private final Integer status;

    /**
     * 메타 데이터를 위한 메시지
     */
    private final String message;
    /**
     * 응답에 대한 데이터
     */
    private final T data;

    /**
     * 응답에 따른 데이터가 없어도 되는 경우 사용
     * <p>
     * 메시지 : Success
     */
    public static ResponseEntity<ApiResponse<Void>> ok() {
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .data(null)
                        .message("Success")
                        .build()
        );
    }

    /**
     * 클라이언트에게 데이터를 전달 할 때 사용<p>
     *
     * @param data 클라이언트에 전달 할 데이터<br>
     *             메시지 : Success
     */
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .status(HttpStatus.OK.value())
                        .data(data)
                        .message("Success")
                        .build()
        );
    }

    /**
     * 클라이언트에게 커스텀 헤더를 추가하여 데이터를 전달 할 때 사용<p>
     *
     * @param data  클라이언트에 전달 할 데이터<br>
     *              메시지 : Success
     * @param key   헤더의 키
     * @param value 헤더의 밸류
     */
    public static <T> ResponseEntity<ApiResponse<T>> okWithCustomHeader(T data, String key, String value) {
        return ResponseEntity.status(HttpStatus.OK)
                .header(key, value)
                .body(
                        ApiResponse.<T>builder()
                                .status(HttpStatus.OK.value())
                                .data(data)
                                .message("Success")
                                .build()
                );
    }

    /**
     * 클라이언트에게 커스텀 헤더쌍들을 추가하여 데이터를 전달 할 때 사용<p>
     *
     * @param data   클라이언트에 전달 할 데이터<br>
     *               메시지 : Success
     * @param header Key에 헤더의 key, Value 에 헤더의 value
     */
    public static <T> ResponseEntity<ApiResponse<T>> okWithCustomHeaders(T data, Map<String, String> header) {
        HttpHeaders headers = new HttpHeaders();

        for (Map.Entry<String, String> map : header.entrySet()) {
            headers.add(map.getKey(), map.getValue());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(
                        ApiResponse.<T>builder()
                                .status(HttpStatus.OK.value())
                                .data(data)
                                .message("Success")
                                .build()
                );
    }

    /**
     * 클라이언트에게 메시지와 데이터를 전달 할 때 사용<p>
     *
     * @param message 직접 입력한 메시지<p>
     * @param data    클라이언트에 전달 할 데이터<p>
     */
    public static <T> ResponseEntity<ApiResponse<T>> okWithMessage(String message, T data) {
        return ResponseEntity.ok(
                ApiResponse.<T>builder()
                        .status(HttpStatus.OK.value())
                        .data(data)
                        .message(message)
                        .build()
        );
    }

    /**
     * 예외 발생시 클라이언트에게 알릴 때 사용<p>
     *
     * @param errorCode 에러코드 목록은 {@link ErrorCode}을 참조<p>
     *                  원하는 에러코드가 없을 때, ErrorCode 새로 추가
     *                  또는{@link ApiResponse#errorCustom(HttpStatus, String) errorCustom} 중 선택하여 사용
     */
    public static <T> ResponseEntity<ApiResponse<T>> error(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(
                        ApiResponse.<T>builder()
                                .status(errorCode.getHttpStatus().value())
                                .data(null)
                                .message(errorCode.getMessage())
                                .build()
                );
    }

    /**
     * 예외 발생시 클라이언트에게 알릴 에러코드 사용<p>
     *
     * @param status  클라이언트에게 전달 할 {@link HttpStatus}<br>
     * @param message 클라이언트에게 전달 할 메시지<br>
     */
    public static <T> ResponseEntity<ApiResponse<T>> errorCustom(HttpStatus status, String message) {
        return ResponseEntity.status(status.value())
                .body(
                        ApiResponse.<T>builder()
                                .status(status.value())
                                .data(null)
                                .message(message)
                                .build()
                );
    }
}
