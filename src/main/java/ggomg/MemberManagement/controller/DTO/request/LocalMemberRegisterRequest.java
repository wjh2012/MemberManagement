package ggomg.MemberManagement.controller.DTO.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocalMemberRegisterRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String nickname;


}
