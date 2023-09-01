package ggomg.MemberManagement;

import ggomg.MemberManagement.controller.DTO.request.LocalMemberRegisterRequest;
import ggomg.MemberManagement.member.Member;
import ggomg.MemberManagement.member.MemberRepository;
import ggomg.MemberManagement.member.MemberService;
import ggomg.MemberManagement.role.Role;
import ggomg.MemberManagement.role.RoleName;
import ggomg.MemberManagement.role.RoleRepository;
import ggomg.MemberManagement.role.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class InitDb {

    private final MemberService memberService;
    private final RoleService roleService;

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;


    @PostConstruct
    public void init() {
//        joinGrantedMember();
//        joinTempMember();
    }

    private void joinGrantedMember() {
        Role role1 = Role.of(RoleName.USER);
        Role role2 = Role.of(RoleName.MANAGER);
        Role role3 = Role.of(RoleName.ADMIN);

        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);

        memberService.joinLocalMember(new LocalMemberRegisterRequest("admin", "admin", "admin"));
        memberService.joinLocalMember(new LocalMemberRegisterRequest("manager", "manager", "manager"));
        memberService.joinLocalMember(new LocalMemberRegisterRequest("member", "member", "member"));

        Member admin = memberRepository.findByUsername("admin").orElseThrow();
        Member manager = memberRepository.findByUsername("manager").orElseThrow();
        Member member = memberRepository.findByUsername("member").orElseThrow();

        roleService.grantRole(admin.getId(), RoleName.ADMIN);
        roleService.grantRole(manager.getId(), RoleName.MANAGER);

        roleService.grantRole(member.getId(), RoleName.MANAGER);
        roleService.grantRole(member.getId(), RoleName.ADMIN);
        roleService.revokeRole(member.getId(), RoleName.ADMIN);
    }

    private void joinTempMember() {

        for (int i = 1; i <= 100; i++) {
            memberService.joinLocalMember(new LocalMemberRegisterRequest(i + "", i + "", i + ""));
        }
    }
}
