package ggomg.MemberManagement;

import ggomg.MemberManagement.domain.login_register.LocalMemberRegisterRequest;
import ggomg.MemberManagement.domain.member.Member;
import ggomg.MemberManagement.domain.member.MemberService;
import ggomg.MemberManagement.domain.member.reposiory.MemberRepository;
import ggomg.MemberManagement.domain.role.RoleName;
import ggomg.MemberManagement.domain.role.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DevInit {

    private final MemberService memberService;
    private final RoleService roleService;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        joinGrantedMember();
    }

    private void joinGrantedMember() {
        try {
            memberService.joinLocalMember(new LocalMemberRegisterRequest("admin", "admin", "admin"));
            memberService.joinLocalMember(new LocalMemberRegisterRequest("manager", "manager", "manager"));
            memberService.joinLocalMember(new LocalMemberRegisterRequest("member", "member", "member"));

            Member admin = memberRepository.findByUsername("admin").orElseThrow();
            Member manager = memberRepository.findByUsername("manager").orElseThrow();
            Member member = memberRepository.findByUsername("member").orElseThrow();

            roleService.grantRole(admin.getId(), RoleName.ADMIN);
            roleService.grantRole(manager.getId(), RoleName.MANAGER);
            roleService.grantRole(member.getId(), RoleName.ADMIN);
            roleService.revokeRole(member.getId(), RoleName.ADMIN);
        } catch (Exception e) {
        }

    }

}
