package ggomg.MemberManagement.security.LocalUser;

import ggomg.MemberManagement.member.Member;
import ggomg.MemberManagement.member.MemberService;
import ggomg.MemberManagement.role.RoleService;
import ggomg.MemberManagement.security.CustomUser;
import ggomg.MemberManagement.security.MemberDTO;
import lombok.RequiredArgsConstructor;
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

        try {
            Member member = memberService.findByUsername(username);
            MemberDTO memberDTO = new MemberDTO(
                    member.getUsername(),
                    member.getPassword(),
                    roleService.buildUserAuthority(member.getId()),
                    member.getNickname()
            );
            return new CustomUser(memberDTO);
        } catch (Exception e) {
            throw new UsernameNotFoundException("사용자를 검색하는 도중 예외 발생: " + username, e);
        }
    }
}
