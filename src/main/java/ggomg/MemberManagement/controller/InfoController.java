package ggomg.MemberManagement.controller;

import ggomg.MemberManagement.controller.DTO.request.NicknameUpdateRequest;
import ggomg.MemberManagement.member.InfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        return "page/member-info";
    }

}
