package ggomg.MemberManagement.exception;

import lombok.Getter;

@Getter
public class AuthorityException extends RuntimeException {

    private final AuthorityError errorCode;

    public AuthorityException(AuthorityError errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
