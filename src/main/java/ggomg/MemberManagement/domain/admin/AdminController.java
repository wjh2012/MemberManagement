package ggomg.MemberManagement.domain.admin;

import ggomg.MemberManagement.exception.AuthorityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("addDummyMember")
    public ResponseEntity<?> addDummyMember() {

        adminService.createDummyMember();
        return ResponseEntity.ok().body("Successfully added dummy member");
    }

    @PostMapping("addDummyMembers")
    public ResponseEntity<?> addDummyMembers() {

        adminService.createDummyMembers();
        return ResponseEntity.ok().body("Successfully added 50 dummy members");
    }

    @PostMapping("deleteMembers")
    public ResponseEntity<?> deleteMembers(@RequestBody AdminDeleteMembersRequest adminDeleteMembersRequest) {

        adminService.deleteMembers(adminDeleteMembersRequest.getSelectedIds());
        return ResponseEntity.ok().body("Successfully delete members");
    }

}