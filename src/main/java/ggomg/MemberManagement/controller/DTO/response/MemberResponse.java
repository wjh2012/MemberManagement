package ggomg.MemberManagement.controller.DTO.response;

import ggomg.MemberManagement.member.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class MemberResponse {

    private Long id;

    private boolean isOauth;

    private String authType;

    private String oauthId;

    private String nickname;

    private String username;

    private LocalDateTime createdDatetime;

    private List<String> roles;

    public static MemberResponse mappedByMember(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .isOauth(member.isOauth())
                .authType(member.getAuthType())
                .oauthId(member.getOauthId())
                .username(member.getUsername())
                .createdDatetime(member.getCreatedDatetime())
                .roles(member.getMemberRoles().stream().map(memberRole -> memberRole.getRole().getRoleName().name())
                        .collect(Collectors.toList()))
                .nickname(member.getNickname())
                .build();
    }
}
