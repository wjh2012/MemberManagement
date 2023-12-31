package ggomg.MemberManagement.API.member;

import ggomg.MemberManagement.domain.login_register.LocalMemberRegisterRequest;
import ggomg.MemberManagement.domain.member.DTO.MemberResponse;
import ggomg.MemberManagement.domain.member.Member;
import ggomg.MemberManagement.domain.member.MemberService;
import ggomg.MemberManagement.exception.RegistrationException;
import ggomg.MemberManagement.security.CustomUser;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class APIMemberController {

    private final APIMemberService apiMemberService;
    private final MemberService memberService;

    @GetMapping("/api/userInfo")
    public SoloResult<MemberResponse> getUserInfo() {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = customUser.getId();

        MemberResponse memberResponse = MemberResponse.mappedByMember(memberService.findById(id));
        SoloResult<MemberResponse> result = new SoloResult<>();
        result.setData(memberResponse);

        return result;
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody LocalMemberRegisterRequest localMemberRegisterRequest) {
        memberService.joinLocalMember(localMemberRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/logged")
    public ResponseEntity<?> checkLoggedIn() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/members")
    public Result<List<APIMemberDTO>> members(@RequestParam int page) {
        Page<Member> members = apiMemberService.getMembers(PageRequest.of(page-1, 8));

        List<APIMemberDTO> memberDTOs = members.stream()
                .map(m -> new APIMemberDTO(m.getId(), m.getNickname(), m.getUsername())).toList();
        Result<List<APIMemberDTO>> result = new Result<>();

        result.setData(memberDTOs);
        result.setTotal(members.getTotalElements());

        return result;
    }

    @GetMapping("/api/members/{id}")
    public SoloResult<MemberResponse> getMemberById(@PathVariable Long id) {

        Member member = apiMemberService.findById(id);
        MemberResponse m = MemberResponse.mappedByMember(member);

        SoloResult<MemberResponse> resultMember = new SoloResult<>();
        resultMember.setData(m);
        return resultMember;

    }

    @Data
    static class Result<T> {
        private T data;
        private Long total;
    }

    @Data
    static class SoloResult<T> {
        private T data;
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<?> handleRegistrationException(RegistrationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
