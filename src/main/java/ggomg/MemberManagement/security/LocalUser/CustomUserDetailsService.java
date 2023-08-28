package ggomg.MemberManagement.security.LocalUser;

import ggomg.MemberManagement.member.Member;
import ggomg.MemberManagement.member.MemberService;
import ggomg.MemberManagement.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.findByUsername(username);
        return new User(member.getId().toString(), member.getPassword(),
                roleService.buildUserAuthority(member.getId()));
    }
}
