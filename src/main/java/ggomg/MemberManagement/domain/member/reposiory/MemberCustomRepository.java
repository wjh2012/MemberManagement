package ggomg.MemberManagement.domain.member.reposiory;

import ggomg.MemberManagement.domain.member.DTO.MemberSearchCondition;
import ggomg.MemberManagement.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberCustomRepository {

    Page<Member> MemberSearchWithPage(MemberSearchCondition memberSearchCondition, Pageable pageable);

}
