package ggomg.MemberManagement.domain.disabledMember;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface DisableMemberRepository extends CrudRepository<DisabledMember, String> {
    Optional<DisabledMember> findByMemberId(String memberId);
}
