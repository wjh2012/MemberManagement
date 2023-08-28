package ggomg.MemberManagement.controller;

import ggomg.MemberManagement.controller.DTO.request.LocalMemberRegisterRequest;
import ggomg.MemberManagement.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final MemberService memberService;

    @GetMapping("/local")
    public String showRegisterForm() {
        return "page/register";
    }

    @PostMapping("/local")
    public String register(LocalMemberRegisterRequest localMemberRegisterRequest) {

        memberService.joinLocalMember(localMemberRegisterRequest);
        return "redirect:/";
    }

    @PostMapping("/local/name-check")
    public boolean usernameCheck(String username) {
        return memberService.isExistByUsername(username);
    }
}
