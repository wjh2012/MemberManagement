package ggomg.MemberManagement.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PaginationTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void joinTempMember() {
        for (int i = 1; i <= 100; i++) {
            memberRepository.save(new Member());
        }
    }

    @Test
    void 페이지네이션_테스트() {

        // When
        Page<Member> resultPage = memberRepository.findAll(PageRequest.of(0, 10));

        // Then
        assertEquals(100, resultPage.getTotalElements());
        assertEquals(10, resultPage.getContent().size());
    }

}
