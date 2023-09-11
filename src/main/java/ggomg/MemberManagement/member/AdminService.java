package ggomg.MemberManagement.member;

import ggomg.MemberManagement.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    public void deleteMember(Long userId) {

        memberRepository.deleteById(userId);
    }

}
