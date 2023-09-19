package ggomg.MemberManagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthorityError implements ErrorCode {

    PERMISSION_EXCEEDED(301, "권한을 초과하였습니다.")
    ;

    private final int code;
    private final String message;

}
