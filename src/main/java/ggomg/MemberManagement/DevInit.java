package ggomg.MemberManagement;

import ggomg.MemberManagement.controller.DTO.request.LocalMemberRegisterRequest;
import ggomg.MemberManagement.member.Member;
import ggomg.MemberManagement.member.MemberRepository;
import ggomg.MemberManagement.member.MemberService;
import ggomg.MemberManagement.role.RoleName;
import ggomg.MemberManagement.role.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
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
            memberService.joinLocalMember(new LocalMemberRegisterRequest("member2", "member2", "member2"));

            Member admin = memberRepository.findByUsername("admin").orElseThrow();
            Member manager = memberRepository.findByUsername("manager").orElseThrow();
            Member member = memberRepository.findByUsername("member").orElseThrow();

            roleService.grantRole(admin.getId(), RoleName.ADMIN);
            roleService.grantRole(manager.getId(), RoleName.MANAGER);

            roleService.grantRole(member.getId(), RoleName.MANAGER);
            roleService.grantRole(member.getId(), RoleName.ADMIN);
            roleService.revokeRole(member.getId(), RoleName.ADMIN);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

}
