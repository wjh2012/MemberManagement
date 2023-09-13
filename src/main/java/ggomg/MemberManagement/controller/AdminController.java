package ggomg.MemberManagement.controller;

import ggomg.MemberManagement.controller.DTO.request.AdminDeleteMembersRequest;
import ggomg.MemberManagement.controller.DTO.request.LocalMemberRegisterRequest;
import ggomg.MemberManagement.member.AdminService;
import ggomg.MemberManagement.member.MemberService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final AdminService adminService;

    @PostMapping("addDummyMember")
    public ResponseEntity<?> addDummyMember() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
        String member = "member" + currentDateTime.format(formatter);

        memberService.joinLocalMember(new LocalMemberRegisterRequest(member, member, member));

        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("addDummyMembers")
    public ResponseEntity<?> addDummyMembers() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmss");
        String member = "member" + currentDateTime.format(formatter);

        for (int i = 0; i < 50; i++) {
            memberService.joinLocalMember(new LocalMemberRegisterRequest(member + i, member + i, member + i));
        }

        return ResponseEntity.ok().body("Successfully added 50 dummy members");
    }

    @PostMapping("deleteMembers")
    public ResponseEntity<?> deleteMembers(@RequestBody AdminDeleteMembersRequest adminDeleteMembersRequest) {

        adminDeleteMembersRequest.getSelectedIds().forEach(id -> adminService.deleteMember(Long.valueOf(id)));

        return ResponseEntity.ok().body("Successfully delete members");
    }

}