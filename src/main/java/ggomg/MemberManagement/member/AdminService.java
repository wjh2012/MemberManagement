package ggomg.MemberManagement.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final MemberRepository memberRepository;

    public void deleteMember(Long userId) {
        memberRepository.deleteById(userId);
    }

}
