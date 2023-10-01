package ggomg.MemberManagement.API.member;

import ggomg.MemberManagement.domain.member.Member;
import ggomg.MemberManagement.domain.member.reposiory.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class APIMemberService {

    private final MemberRepository memberRepository;

    public Page<Member> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }
}
