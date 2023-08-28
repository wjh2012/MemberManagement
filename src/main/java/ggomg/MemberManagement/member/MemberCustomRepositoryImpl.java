package ggomg.MemberManagement.member;

import static ggomg.MemberManagement.member.QMember.member;
import static ggomg.MemberManagement.role.QMemberRole.memberRole;
import static ggomg.MemberManagement.role.QRole.role;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ggomg.MemberManagement.member.DTO.MemberSearchCondition;
import ggomg.MemberManagement.role.RoleName;
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
                .join(member.memberRoles, memberRole).fetchJoin()
                .join(memberRole.role, role).fetchJoin()
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
