package ggomg.MemberManagement.domain.login_register;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocalMemberRegisterRequest {

    @NotNull
    @Size(min = 4, max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String username;

    @NotNull
    @Size(min = 8, max = 32)
    private String password;

    @NotNull
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$")
    private String nickname;
}
