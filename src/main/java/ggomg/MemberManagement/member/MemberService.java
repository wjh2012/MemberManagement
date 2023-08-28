package ggomg.MemberManagement.member;

import static ggomg.MemberManagement.member.MemberPropValidator.validateNickname;
import static ggomg.MemberManagement.member.MemberPropValidator.validatePassword;
import static ggomg.MemberManagement.member.MemberPropValidator.validateUsername;

import ggomg.MemberManagement.controller.DTO.request.LocalMemberRegisterRequest;
import ggomg.MemberManagement.controller.DTO.request.MemberSearchRequest;
import ggomg.MemberManagement.member.DTO.LocalMemberRegisterEssentials;
import ggomg.MemberManagement.member.DTO.MemberSearchCondition;
import ggomg.MemberManagement.role.RoleName;
import ggomg.MemberManagement.role.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long joinLocalMember(LocalMemberRegisterRequest localMemberRegisterRequest) {

        String username = localMemberRegisterRequest.getUsername();
        String password = localMemberRegisterRequest.getPassword();
        String nickname = localMemberRegisterRequest.getNickname();

        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("already exist");
        }

        validateUsername(username);
        validatePassword(password);
        validateNickname(nickname);

        String encryptedPassword = bCryptPasswordEncoder.encode(password);

        LocalMemberRegisterEssentials localMemberRegisterEssentials =
                new LocalMemberRegisterEssentials(
                        username,
                        encryptedPassword,
                        nickname
                );

        Member member = Member.createByUsernamePassword(localMemberRegisterEssentials);

        memberRepository.save(member);
        roleService.grantRole(member.getId(), RoleName.USER);

        return member.getId();
    }

    public Long joinOAuth2Member(String oauthType, String oauthId) {
        if (memberRepository.existsByOauthId(oauthId)) {
            throw new IllegalArgumentException("already exist");
        }

        Member member = Member.createByOAuth2(oauthType, oauthId);

        memberRepository.save(member);
        return member.getId();
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow();
    }

    public Member findByOAuthId(String oauthId) {
        return memberRepository.findByOauthId(oauthId).orElseThrow();
    }

    public boolean isExistByOAuthId(String oauthId) {
        return memberRepository.existsByOauthId(oauthId);
    }

    public boolean isExistByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public Page<Member> searchMember(MemberSearchRequest memberSearchRequest) {
        MemberSearchCondition memberSearchCondition = memberSearchRequest.getMemberSearchCondition();
        PageRequest pageRequest =
                PageRequest.of(
                        memberSearchRequest.getOffsetPagingRequest().getPage() - 1,
                        memberSearchRequest.getOffsetPagingRequest().getPageSize()
                );
        return memberRepository.MemberSearchWithPage(memberSearchCondition, pageRequest);
    }
}
