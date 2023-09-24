package ggomg.MemberManagement.domain.profile;

import ggomg.MemberManagement.domain.member.Member;
import ggomg.MemberManagement.domain.member.reposiory.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final MemberRepository memberRepository;

    public Member updateNickname(Long userId, String nickname) {

        Member member = memberRepository.findById(userId).orElseThrow();
        member.updateNickname(nickname);

        memberRepository.save(member);
        return member;
    }

}
