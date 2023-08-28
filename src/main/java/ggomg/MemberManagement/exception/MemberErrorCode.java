package ggomg.MemberManagement.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberErrorCode implements ErrorCode{
    LOGIN_FAIL(101, "로그인 실패"),

    REGISTER_FAIL_USERNAME_LENGTH(211, "아이디는 5자 이상 17자 이하여야 합니다."),
    REGISTER_FAIL_USERNAME_CHAR(211, "아이디는 영문, 숫자만 가능합니다."),

    REGISTER_FAIL_PASSWORD_LENGTH(221, "비밀번호 길이는 4"),
    REGISTER_FAIL_PASSWORD_CHAR(221, "회원가입 실패"),

    REGISTER_FAIL_NICKNAME_LENGTH(231, "회원가입 실패"),
    REGISTER_FAIL_NICKNAME_CHAR(231, "회원가입 실패"),
    ;


    private final int code;
    private final String message;

}
