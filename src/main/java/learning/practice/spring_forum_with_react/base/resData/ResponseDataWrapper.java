package learning.practice.spring_forum_with_react.base.resData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDataWrapper<T> {
    private String resultCode;
    private String msg;
    private T data;

    private static final String SUCCESS_CODE_PREFIX = "S-";
    private static final String FAIL_CODE_PREFIX = "F-";
    private static final String BASIC_SUCCESS_MSG = "성공";
    private static final String BASIC_FAIL_MSG = "실패";
    public static <T> ResponseDataWrapper<T> of(String resultCode, String msg, T data) {
        return new ResponseDataWrapper<>(resultCode, msg, data);
    }

    public static <T> ResponseDataWrapper<T> of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static <T> ResponseDataWrapper<T> successOf(String resultCode, String resultMsg, T data) {
        return of(SUCCESS_CODE_PREFIX + resultCode, resultMsg, data);
    }

    public static <T> ResponseDataWrapper<T> failOf(String resultCode, String resultMsg, T data) {
        return of(FAIL_CODE_PREFIX + resultCode, resultMsg, data);
    }

    public static <T> ResponseDataWrapper<T> validate(String resultCode, String resultMsg, boolean isAvailable) {
        if (isAvailable) {
            return successOf(resultCode, resultMsg, null);
        }
        return failOf(resultCode, resultMsg, null);
    }

    public boolean isSuccess() {
        return resultCode.startsWith(SUCCESS_CODE_PREFIX);
    }

    public boolean isFailed() {
        return !isSuccess();
    }
}
