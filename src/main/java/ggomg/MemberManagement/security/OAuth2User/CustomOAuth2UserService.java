package ggomg.MemberManagement.security.OAuth2User;

import ggomg.MemberManagement.member.Member;
import ggomg.MemberManagement.member.MemberService;
import ggomg.MemberManagement.role.RoleName;
import ggomg.MemberManagement.role.RoleService;
import ggomg.MemberManagement.security.CustomUser;
import ggomg.MemberManagement.security.MemberDTO;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String NAVER_CLIENT = "Naver";

    private final MemberService memberService;
    private final RoleService roleService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User loadedUser = super.loadUser(userRequest);

        Map<String, Object> userAttributes = loadedUser.getAttributes();
        String clientName = userRequest.getClientRegistration().getClientName();
        String oauthId = null;

        if (clientName.equals(NAVER_CLIENT)) {
            userAttributes = (Map<String, Object>) loadedUser.getAttributes().get("response");
            oauthId = userAttributes.get("id").toString();
        } else {
            oauthId = loadedUser.getName();
        }

        String nickname = userAttributes.getOrDefault("name", clientName + oauthId.substring(0, 4)).toString();
        if (!memberService.isExistByOauthId(oauthId)) {
            Long memberId = memberService.joinOAuth2Member(clientName, oauthId, nickname);
            roleService.grantRole(memberId, RoleName.USER);
        }

        Member member = memberService.findByOAuthId(oauthId);

        MemberDTO memberDTO = MemberDTO.builder()
                .id(member.getId())
                .username(member.getOauthId())
                .authorities(roleService.buildUserAuthority(member.getId()))
                .nickname(member.getNickname())
                .build();

        return new CustomUser(memberDTO);
    }

}
