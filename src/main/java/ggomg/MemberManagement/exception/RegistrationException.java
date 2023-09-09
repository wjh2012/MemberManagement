package ggomg.MemberManagement.exception;

import lombok.Getter;

@Getter
public class RegistrationException extends RuntimeException {

    private final RegisterErrorCode errorCode;

    public RegistrationException(RegisterErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
