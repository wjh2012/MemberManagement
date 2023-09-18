package ggomg.MemberManagement.domain.role;

import lombok.Getter;

@Getter
public enum RoleName {
    ADMIN(1L),
    MANAGER(2L),
    USER(3L);

    private final Long id;

    RoleName(Long id) {
        this.id = id;
    }
}
