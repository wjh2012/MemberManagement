package ggomg.MemberManagement.member;

import ggomg.MemberManagement.member.DTO.LocalMemberRegisterEssentials;
import ggomg.MemberManagement.role.MemberRole;
import ggomg.MemberManagement.role.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isOauth;

    private String authType;

    private String oauthId;

    private String nickname;

    private String username;

    private String password;

    @CreatedDate
    private LocalDateTime createdDatetime;

    @BatchSize(size = 50)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberRole> memberRoles = new ArrayList<>();

    public static Member createByUsernamePassword(LocalMemberRegisterEssentials localMemberRegisterEssentials) {

        return Member.builder()
                .username(localMemberRegisterEssentials.getUsername())
                .password(localMemberRegisterEssentials.getEncryptedPassword())
                .nickname(localMemberRegisterEssentials.getNickname())
                .isOauth(false)
                .authType("Local")
                .oauthId(null)
                .memberRoles(new ArrayList<>())
                .build();
    }

    public static Member createByOAuth2(String authType, String oauthId) {
        return Member.builder()
                .username(null)
                .password(null)
                .isOauth(true)
                .authType(authType)
                .oauthId(oauthId)
                .build();
    }

    // 연관관계 편의 메서드
    public void addRole(Role role) {
        MemberRole memberRole = MemberRole.of(this, role);
        this.memberRoles.add(memberRole);
    }

    public void removeRole(Role role) {
        MemberRole memberRoleToRemove = null;

        for (MemberRole memberRole : memberRoles) {
            if (memberRole.getRole().equals(role)) {
                memberRoleToRemove = memberRole;
                break;
            }
        }

        if (memberRoleToRemove != null) {
            this.memberRoles.remove(memberRoleToRemove);
        }
    }
}
