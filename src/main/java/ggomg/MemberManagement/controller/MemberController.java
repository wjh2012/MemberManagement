package ggomg.MemberManagement.controller;

import ggomg.MemberManagement.controller.DTO.request.MemberSearchRequest;
import ggomg.MemberManagement.controller.DTO.response.MemberResponse;
import ggomg.MemberManagement.controller.DTO.response.PagingResponse;
import ggomg.MemberManagement.controller.DTO.response.RecordInfo;
import ggomg.MemberManagement.member.Member;
import ggomg.MemberManagement.member.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("list")
    public String showMemberSearch() {
        return "page/member-list";
    }

//    @PostMapping("list")
//    public String memberSearch(Model model, @RequestBody MemberSearchRequest memberSearchRequest) {
//
//        Page<Member> memberPage = memberService.searchMember(memberSearchRequest);
//        List<MemberResponse> members = memberPage.stream().map(MemberResponse::mappedByMember).toList();
//
//        PagingResponse<MemberResponse> response = new PagingResponse<>();
//        response.setList(members);
//        response.setRecordInfo(new RecordInfo((int) memberPage.getTotalElements()));
//
//        model.addAttribute("response", response);
//
//        return "/contents/member/_list-test";
//    }

    @PostMapping("list")
    public ResponseEntity<PagingResponse<MemberResponse>> memberSearch(
            @RequestBody MemberSearchRequest memberSearchRequest) {

        Page<Member> memberPage = memberService.searchMember(memberSearchRequest);
        List<MemberResponse> members = memberPage.stream().map(MemberResponse::mappedByMember).toList();

        PagingResponse<MemberResponse> response = new PagingResponse<>();
        response.setList(members);
        response.setRecordInfo(new RecordInfo((int) memberPage.getTotalElements()));

        return ResponseEntity.ok(response);
    }

}
