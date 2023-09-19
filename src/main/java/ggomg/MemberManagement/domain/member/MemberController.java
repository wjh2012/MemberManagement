package ggomg.MemberManagement.domain.member;

import ggomg.MemberManagement.domain.member.DTO.MemberResponse;
import ggomg.MemberManagement.domain.member.DTO.MemberSearchRequest;
import ggomg.MemberManagement.domain.member.DTO.PagingResponse;
import ggomg.MemberManagement.domain.member.DTO.RecordInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String getMembers() {
        return "page/member-list";
    }

    @PostMapping
    public ResponseEntity<PagingResponse<MemberResponse>> getMembersByConditions(
            @RequestBody MemberSearchRequest memberSearchRequest) {

        Page<Member> memberPage = memberService.searchMember(memberSearchRequest);
        List<MemberResponse> members = memberPage.stream().map(MemberResponse::mappedByMember).toList();

        PagingResponse<MemberResponse> response = new PagingResponse<>();
        response.setList(members);
        response.setRecordInfo(new RecordInfo((int) memberPage.getTotalElements()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public String getMemberById(@PathVariable Long id, Model model) {

        Member member = memberService.findById(id);
        MemberResponse memberResponse = MemberResponse.mappedByMember(member);
        model.addAttribute("member", memberResponse);

        return "page/member";

    }
}
