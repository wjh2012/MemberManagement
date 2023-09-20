package ggomg.MemberManagement.domain.admin;

import ggomg.MemberManagement.disabledMember.DisableMemberRepository;
import ggomg.MemberManagement.disabledMember.DisabledMember;
import ggomg.MemberManagement.domain.member.DTO.LocalMemberRegisterEssentials;
import ggomg.MemberManagement.domain.member.Member;
import ggomg.MemberManagement.domain.member.reposiory.MemberRepository;
import ggomg.MemberManagement.domain.role.MemberRole;
import ggomg.MemberManagement.domain.role.Role;
import ggomg.MemberManagement.exception.AuthorityError;
import ggomg.MemberManagement.exception.AuthorityException;
import ggomg.MemberManagement.security.CustomUser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final MemberRepository memberRepository;
    private final DisableMemberRepository disableMemberRepository;

    public void createDummyMember() {

        String name = generateMemberName();
        createDummyMember(name);
    }

    public void createDummyMembers() {

        String name = generateMemberName();
        for (int i = 0; i < 50; i++) {
            createDummyMember(name + i);
        }
    }

    private String generateMemberName() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");

        return "member" + currentDateTime.format(formatter);
    }

    private void createDummyMember(String value) {
        memberRepository.save(Member.createByUsernamePassword(
                LocalMemberRegisterEssentials.of(value, value, value)
        ));
    }

    @Transactional
    public void deleteMembers(List<Long> targets) {

        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long requestUserId = customUser.getId();

        targets.forEach(targetId -> deleteMember(requestUserId, targetId));
    }

    private void deleteMember(Long requestUserId, Long targetId) {
        Member fromMember = memberRepository.findById(requestUserId).orElseThrow();
        Member toMember = memberRepository.findById(targetId).orElseThrow();

        Long fromHighestRoleId = findHighestRoleId(fromMember);
        Long toHighestRoleId = findHighestRoleId(toMember);

        log.info(fromHighestRoleId.toString());
        log.info(toHighestRoleId.toString());

        if (fromHighestRoleId >= toHighestRoleId) {
            throw new AuthorityException(AuthorityError.PERMISSION_EXCEEDED);
        }

        memberRepository.deleteById(targetId);
        disableMemberRepository.save(DisabledMember.of(targetId.toString()));
    }

    private Long findHighestRoleId(Member member) {
        List<MemberRole> memberRoles = member.getMemberRoles();

        return memberRoles.stream()
                .map(MemberRole::getRole)
                .min(Comparator.comparingLong(r -> r.getRoleName().getId()))
                .map(Role::getId)
                .orElse(99L);
    }

}
