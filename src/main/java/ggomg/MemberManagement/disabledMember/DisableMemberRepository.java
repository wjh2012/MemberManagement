package ggomg.MemberManagement.disabledMember;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface DisableMemberRepository extends CrudRepository<DisabledMember, Long> {
    Optional<DisabledMember> findByMemberId(Long memberId);
}
