package ggomg.MemberManagement.role;

import ggomg.MemberManagement.member.Member;
import ggomg.MemberManagement.member.MemberRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;

    public void grantRole(Long memberId, RoleName roleName) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Role role = roleRepository.findByRoleName(roleName).orElseThrow();

        member.addRole(role);
    }

    public void revokeRole(Long memberId, RoleName roleName) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Role role = roleRepository.findByRoleName(roleName).orElseThrow();

        member.removeRole(role);
    }

    public Set<GrantedAuthority> buildUserAuthority(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        List<MemberRole> memberRoles = member.getMemberRoles();
        Set<GrantedAuthority> setAuths = new HashSet<>();

        for (MemberRole memberRole : memberRoles) {
            setAuths.add(new SimpleGrantedAuthority(memberRole.getRole().getRoleName().name()));
        }

        return setAuths;
    }
}
