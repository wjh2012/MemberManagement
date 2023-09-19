package ggomg.MemberManagement.security.LocalUser;

import ggomg.MemberManagement.domain.member.Member;
import ggomg.MemberManagement.domain.member.MemberService;
import ggomg.MemberManagement.domain.role.RoleService;
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
            MemberDTO memberDTO = MemberDTO.builder()
                    .id(member.getId())
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .authorities(roleService.buildUserAuthority(member.getId()))
                    .nickname(member.getNickname())
                    .build();

            return new CustomUser(memberDTO);
        } catch (Exception e) {
            throw new UsernameNotFoundException("사용자를 검색하는 도중 예외 발생: " + username, e);
        }
    }

    public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {

        try {
            Member member = memberService.findById(userId);
            MemberDTO memberDTO = MemberDTO.builder()
                    .id(member.getId())
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .authorities(roleService.buildUserAuthority(member.getId()))
                    .nickname(member.getNickname())
                    .build();

            return new CustomUser(memberDTO);
        } catch (Exception e) {
            throw new UsernameNotFoundException("사용자를 검색하는 도중 예외 발생: " + userId, e);
        }
    }
}
