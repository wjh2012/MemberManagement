package ggomg.MemberManagement.security.OAuth2User;

import ggomg.MemberManagement.member.MemberService;
import ggomg.MemberManagement.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

//@Component
@Slf4j
public class ProxyOIDCUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final OidcUserService oidcUserService;
    private final MemberService memberService;
    private final RoleService roleService;

    @Autowired
    public ProxyOIDCUserService(MemberService memberService, RoleService roleService) {
        this.oidcUserService = new OidcUserService();
        this.memberService = memberService;
        this.roleService = roleService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("***** load User *****");
        OidcUser loadedUser = oidcUserService.loadUser(userRequest);

        return loadedUser;
    }
}
