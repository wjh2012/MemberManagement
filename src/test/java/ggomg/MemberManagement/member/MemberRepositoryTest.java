package ggomg.MemberManagement.member;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    void joinTempMember(int n) {
        for (int i = 1; i <= n; i++) {
            memberRepository.save(new Member());
        }
    }

    @Test
    void findAllMember() {
        joinTempMember(5);

        List<Member> members = memberRepository.findAll();
        assertEquals(5, members.size());
    }
}