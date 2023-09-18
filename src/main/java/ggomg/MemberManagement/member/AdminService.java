package ggomg.MemberManagement.member;

import ggomg.MemberManagement.disabledMember.DisableMemberRepository;
import ggomg.MemberManagement.disabledMember.DisabledMember;
import ggomg.MemberManagement.role.MemberRole;
import ggomg.MemberManagement.role.Role;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final MemberRepository memberRepository;
    private final DisableMemberRepository disableMemberRepository;

    @Transactional
    public void deleteMembers(Long from, List<Long> targets) {
        targets.forEach(t -> deleteMember(from, t));
    }

    private void deleteMember(Long from, Long target) {
        Member fromMember = memberRepository.findById(from).orElseThrow();
        Member toMember = memberRepository.findById(target).orElseThrow();

        Long fromHighestRoleId = findHighestRoleId(fromMember);
        Long toHighestRoleId = findHighestRoleId(toMember);

        log.info(fromHighestRoleId.toString());
        log.info(toHighestRoleId.toString());

        if (fromHighestRoleId >= toHighestRoleId) {
            throw new IllegalArgumentException();
        }
        memberRepository.deleteById(target);
        disableMemberRepository.save(DisabledMember.of(target));
    }

    private Long findHighestRoleId(Member member) {
        List<MemberRole> memberRoles = member.getMemberRoles();

        return memberRoles.stream()
                .map(MemberRole::getRole)
                .min(Comparator.comparingLong(r -> r.getRoleName().getId()))
                .map(Role::getId)
                .orElse(null);
    }

}
