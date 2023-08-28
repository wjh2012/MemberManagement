package ggomg.MemberManagement.member;

import static ggomg.MemberManagement.member.QMember.member;
import static ggomg.MemberManagement.role.QMemberRole.memberRole;
import static ggomg.MemberManagement.role.QRole.role;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ggomg.MemberManagement.member.DTO.MemberSearchCondition;
import ggomg.MemberManagement.role.RoleName;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberCustomRepositoryImplTest {

    @Autowired
    private JPAQueryFactory query;

    @Autowired
    MemberRepository memberRepository;

    void joinTempMember(int n) {
        for (int i = 1; i <= n; i++) {
            memberRepository.save(new Member());
        }
    }

    @Test
    void queryTest() {
        MemberSearchCondition memberSearchCondition = MemberSearchCondition.builder().build();

        List<Member> result = query
                .select(member)
                .from(member)
                .join(member.memberRoles, memberRole).fetchJoin()
                .join(memberRole.role, role).fetchJoin()
                .where(
                        isOauthEq(memberSearchCondition.getIsOauth()),
                        authTypeEq(memberSearchCondition.getAuthType()),
                        oauthIdEq(memberSearchCondition.getOauthId()),
                        nicknameEq(memberSearchCondition.getNickname()),
                        usernameEq(memberSearchCondition.getUsername()),
                        memberRoleEq(memberSearchCondition.getRole()))
                .fetch();

    }

    private BooleanExpression isOauthEq(Boolean isOauth) {
        if (isOauth == null) {
            return null;
        }
        return member.isOauth.eq(isOauth);
    }

    private BooleanExpression authTypeEq(String authType) {
        if (authType == null) {
            return null;
        }
        return member.authType.eq(authType);
    }

    private BooleanExpression oauthIdEq(String oauthId) {
        if (oauthId == null) {
            return null;
        }
        return member.oauthId.eq(oauthId);
    }

    private BooleanExpression nicknameEq(String nickname) {
        if (nickname == null) {
            return null;
        }
        return member.nickname.eq(nickname);
    }

    private BooleanExpression usernameEq(String username) {
        if (username == null) {
            return null;
        }
        return member.username.eq(username);
    }

    private BooleanExpression memberRoleEq(String role) {
        if (role == null) {
            return null;
        }
        return member.memberRoles.any().role.roleName.eq(RoleName.valueOf(role));
    }
}