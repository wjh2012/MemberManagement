package ggomg.MemberManagement.member.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2MemberRegisterEssentials {

    private String authType;
    private String oauthId;
    private String nickname;

    public static OAuth2MemberRegisterEssentials of(String authType, String oauthId, String nickname) {
        return new OAuth2MemberRegisterEssentials(authType, oauthId, nickname);
    }
}
