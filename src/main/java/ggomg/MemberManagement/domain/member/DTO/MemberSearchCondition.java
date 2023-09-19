package ggomg.MemberManagement.domain.member.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSearchCondition {

    private Boolean isOauth;

    private String authType;

    private String oauthId;

    private String nickname;

    private String username;

    private String createdDatetime;

    private String role;

}
