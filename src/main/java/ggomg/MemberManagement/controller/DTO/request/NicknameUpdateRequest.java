package ggomg.MemberManagement.controller.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NicknameUpdateRequest {

    private Long fromId;

    @NotNull
    private Long targetId;

    @NotNull
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$")
    private String nickname;

}
