package ggomg.MemberManagement.security.OAuth2User;

import ggomg.MemberManagement.member.Member;
import ggomg.MemberManagement.member.MemberService;
import ggomg.MemberManagement.role.RoleName;
import ggomg.MemberManagement.role.RoleService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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
        Set<GrantedAuthority> authorities;
        Map<String, Object> userAttributes;
        String userNameAttributeName;

        String clientName = userRequest.getClientRegistration().getClientName();
        if (clientName.equals(NAVER_CLIENT)) {
            userAttributes = (Map<String, Object>) loadedUser.getAttributes().get("response");
            userAttributes.put("response", userAttributes.get("id").toString());
        } else {
            userAttributes = new HashMap<>(loadedUser.getAttributes());
        }

        userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        String oauthId = userAttributes.get(userNameAttributeName).toString();

        if (!memberService.isExistByOauthId(oauthId)) {
            Long memberId = memberService.joinOAuth2Member(clientName, oauthId);
            roleService.grantRole(memberId, RoleName.USER);
        }

        Member member = memberService.findByOAuthId(oauthId);
        authorities = roleService.buildUserAuthority(member.getId());

        userAttributes.put("id", member.getId());
        userNameAttributeName = "id";

        return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);
    }

}
