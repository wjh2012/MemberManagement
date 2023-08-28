package ggomg.MemberManagement.member;

import ggomg.MemberManagement.member.DTO.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberCustomRepository {

    Page<Member> MemberSearchWithPage(MemberSearchCondition memberSearchCondition, Pageable pageable);

}
