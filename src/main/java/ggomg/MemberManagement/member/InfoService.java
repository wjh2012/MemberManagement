package ggomg.MemberManagement.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InfoService {

    private final MemberRepository memberRepository;

    public void updateNickname(Long userId, String nickname) {

        Member member = memberRepository.findById(userId).orElseThrow();
        member.updateNickname(nickname);
        memberRepository.save(member);
    }

}
