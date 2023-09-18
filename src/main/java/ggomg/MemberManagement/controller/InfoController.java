package ggomg.MemberManagement.controller;

import ggomg.MemberManagement.controller.DTO.request.NicknameUpdateRequest;
import ggomg.MemberManagement.member.InfoService;
import ggomg.MemberManagement.security.LocalUser.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class InfoController {

    private final InfoService infoService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("myPage")
    public String myPage() {
        return "page/member-info";
    }

    @PostMapping("nickname/update")
    public String updateNickname(@Valid NicknameUpdateRequest nicknameUpdateRequest) {

        infoService.updateNickname(
                nicknameUpdateRequest.getTargetId(),
                nicknameUpdateRequest.getNickname()
        );

        UserDetails reloadedUser = customUserDetailsService.loadUserByUserId(nicknameUpdateRequest.getTargetId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(reloadedUser, auth.getCredentials(),
                auth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "page/member-info";
    }

}
