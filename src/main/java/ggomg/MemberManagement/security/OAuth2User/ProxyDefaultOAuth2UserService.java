package ggomg.MemberManagement.security.OAuth2User;

import ggomg.MemberManagement.member.Member;
import ggomg.MemberManagement.member.MemberService;
import ggomg.MemberManagement.role.RoleName;
import ggomg.MemberManagement.role.RoleService;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProxyDefaultOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String NAVER_CLIENT = "Naver";
    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final MemberService memberService;
    private final RoleService roleService;

    @Autowired
    public ProxyDefaultOAuth2UserService(MemberService memberService, RoleService roleService) {
        this.defaultOAuth2UserService = new DefaultOAuth2UserService();
        this.memberService = memberService;
        this.roleService = roleService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("***** load User *****");
        OAuth2User loadedUser = defaultOAuth2UserService.loadUser(userRequest);

        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
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

        if (!memberService.isExistByOAuthId(oauthId)) {
            Long memberId = memberService.joinOAuth2Member(clientName, oauthId);
            roleService.grantRole(memberId, RoleName.USER);
        }

        Member member = memberService.findByOAuthId(oauthId);
        authorities = roleService.buildUserAuthority(member.getId());

        return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);
    }

    private void changeUserAttributeName(String oauthId, Map<String, Object> userAttributes,
                                         String userNameAttributeName) {
        Long MemberId = memberService.findByOAuthId(oauthId).getId();
        userAttributes.replace(userNameAttributeName, MemberId);
    }
}

