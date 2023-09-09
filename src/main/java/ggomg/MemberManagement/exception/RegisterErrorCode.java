package ggomg.MemberManagement.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegisterErrorCode implements ErrorCode {

    REGISTER_FAIL_DUPLICATED_USERNAME(201, "이미 존재하는 아이디입니다."),
    REGISTER_FAIL_DUPLICATED_NICKNAME(202, "이미 존재하는 닉네임입니다."),

    REGISTER_FAIL_USERNAME(211, "아이디는 " + 4 + "자 이상 " + 16 + "자 이하여야 하며 영문 또는 숫자만 가능합니다."),
    REGISTER_FAIL_PASSWORD(221, "비밀번호는 " + 4 + "자 이상 " + 32 + "자 이하여야 하며 공백이 없어야 합니다."),
    REGISTER_FAIL_NICKNAME(231, "닉네임은 " + 1 + "자 이상 " + 16 + "자 이하여야 하며 한글/영문/숫자만 가능합니다.");


    private final int code;
    private final String message;

}
