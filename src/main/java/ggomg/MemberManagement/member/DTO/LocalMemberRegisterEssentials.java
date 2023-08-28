package ggomg.MemberManagement.member.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocalMemberRegisterEssentials {

    @NotNull
    private String username;
    @NotNull
    private String encryptedPassword;
    @NotNull
    private String nickname;

}
