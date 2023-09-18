package ggomg.MemberManagement.domain.member.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalMemberRegisterEssentials {

    private String username;
    private String encryptedPassword;
    private String nickname;

    public static LocalMemberRegisterEssentials of(String username, String password, String nickname) {
        return new LocalMemberRegisterEssentials(username, password, nickname);
    }

}
