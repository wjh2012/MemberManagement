package ggomg.MemberManagement.role;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRole extends EntityPathBase<Role> {

    private static final long serialVersionUID = 917182626L;

    public static final QRole role = new QRole("role");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<MemberRole, QMemberRole> memberRoles = this.<MemberRole, QMemberRole>createList("memberRoles", MemberRole.class, QMemberRole.class, PathInits.DIRECT2);

    public final EnumPath<RoleName> roleName = createEnum("roleName", RoleName.class);

    public QRole(String variable) {
        super(Role.class, forVariable(variable));
    }

    public QRole(Path<? extends Role> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRole(PathMetadata metadata) {
        super(Role.class, metadata);
    }

}

