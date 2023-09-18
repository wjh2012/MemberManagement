package ggomg.MemberManagement.security.OAuth2User;

import ggomg.MemberManagement.domain.member.Member;
import ggomg.MemberManagement.domain.member.MemberService;
import ggomg.MemberManagement.domain.role.RoleService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Slf4j
public class ProxyOIDCUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final OidcUserService oidcUserService;
    private final MemberService memberService;
    private final RoleService roleService;

    public ProxyOIDCUserService(MemberService memberService, RoleService roleService) {
        this.oidcUserService = new OidcUserService();
        this.memberService = memberService;
        this.roleService = roleService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("***** load User *****");
        OidcUser loadedUser = oidcUserService.loadUser(userRequest);

        Set<GrantedAuthority> authorities;
        Map<String, Object> userAttributes;
        String userNameAttributeName;
        String clientName = userRequest.getClientRegistration().getClientName();

        userAttributes = new HashMap<>(loadedUser.getAttributes());
        userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        String oauthId = userAttributes.get(userNameAttributeName).toString();

        if (!memberService.isExistByOauthId(oauthId)) {
//            Long memberId = memberService.joinOAuth2Member(clientName, oauthId);
//            roleService.grantRole(memberId, RoleName.USER);
        }
        Member member = memberService.findByOAuthId(oauthId);
        authorities = roleService.buildUserAuthority(member.getId());

        return new DefaultOidcUser(authorities, userRequest.getIdToken(), loadedUser.getUserInfo());
    }
}
