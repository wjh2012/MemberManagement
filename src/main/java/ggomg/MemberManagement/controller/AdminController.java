package ggomg.MemberManagement.controller;

import ggomg.MemberManagement.controller.DTO.request.LocalMemberRegisterRequest;
import ggomg.MemberManagement.member.MemberService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/addDummyMember")
    public String addDummyMember() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
        String member = "member" + currentDateTime.format(formatter);

        memberService.joinLocalMember(new LocalMemberRegisterRequest(member, member, member));

        return "page/member-list";
    }

    @GetMapping("/addDummyMembers")
    public String addDummyMembers() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
        String member = "member" + currentDateTime.format(formatter);

        for (int i = 0; i < 50; i++) {
            memberService.joinLocalMember(new LocalMemberRegisterRequest(member + i, member + i, member + i));
        }

        return "page/member-list";
    }
}