package ggomg.MemberManagement.domain.member.reposiory;

import static ggomg.MemberManagement.domain.member.QMember.member;
import static ggomg.MemberManagement.domain.role.QMemberRole.memberRole;
import static ggomg.MemberManagement.domain.role.QRole.role;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ggomg.MemberManagement.domain.member.DTO.MemberSearchCondition;
import ggomg.MemberManagement.domain.member.Member;
import ggomg.MemberManagement.domain.role.RoleName;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Member> MemberSearchWithPage(MemberSearchCondition memberSearchCondition, Pageable pageable) {
        List<Member> members = query
                .select(member)
                .from(member)
                .leftJoin(member.memberRoles, memberRole).fetchJoin()
                .leftJoin(memberRole.role, role).fetchJoin()
                .where(
                        isOauthEq(memberSearchCondition.getIsOauth()),
                        authTypeEq(memberSearchCondition.getAuthType()),
                        nicknameEq(memberSearchCondition.getNickname()),
                        memberRoleEq(memberSearchCondition.getRole()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(member.id.asc())
                .fetch();

        Long count = countConditionalMember(memberSearchCondition);

        return new PageImpl<>(members, pageable, count);
    }

    private Long countConditionalMember(MemberSearchCondition memberSearchCondition) {
        return query
                .select(member.count())
                .from(member)
                .where(
                        isOauthEq(memberSearchCondition.getIsOauth()),
                        authTypeEq(memberSearchCondition.getAuthType()),
                        nicknameEq(memberSearchCondition.getNickname()),
                        memberRoleEq(memberSearchCondition.getRole()))
                .fetchOne();
    }

    private BooleanExpression getNextId(Long cursor) {
        if (cursor == null) {
            return null;
        }
        return member.id.gt(cursor);
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
        return member.nickname.contains(nickname);
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
