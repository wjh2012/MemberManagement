package ggomg.MemberManagement.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 981727266L;

    public static final QMember member = new QMember("member1");

    public final StringPath authType = createString("authType");

    public final DateTimePath<java.time.LocalDateTime> createdDatetime = createDateTime("createdDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isOauth = createBoolean("isOauth");

    public final ListPath<ggomg.MemberManagement.role.MemberRole, ggomg.MemberManagement.role.QMemberRole> memberRoles = this.<ggomg.MemberManagement.role.MemberRole, ggomg.MemberManagement.role.QMemberRole>createList("memberRoles", ggomg.MemberManagement.role.MemberRole.class, ggomg.MemberManagement.role.QMemberRole.class, PathInits.DIRECT2);

    public final StringPath nickname = createString("nickname");

    public final StringPath oauthId = createString("oauthId");

    public final StringPath password = createString("password");

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

