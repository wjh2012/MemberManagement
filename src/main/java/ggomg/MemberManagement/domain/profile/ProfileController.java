package ggomg.MemberManagement.domain.profile;

import ggomg.MemberManagement.domain.member.DTO.MemberResponse;
import ggomg.MemberManagement.domain.member.Member;
import ggomg.MemberManagement.domain.member.MemberService;
import ggomg.MemberManagement.domain.session.SessionService;
import ggomg.MemberManagement.security.CustomUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final MemberService memberService;
    private final ProfileService profileService;
    private final SessionService sessionService;

    @GetMapping({"", "update"})
    public String myPage(Model model) {

        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = memberService.findById(customUser.getId());
        MemberResponse memberResponse = MemberResponse.mappedByMember(member);
        model.addAttribute("member", memberResponse);

        return "page/profile";
    }

    @PostMapping("nickname/update")
    public String updateNickname(@Valid NicknameUpdateRequest nicknameUpdateRequest, HttpServletRequest request,
                                 HttpServletResponse response) {

        String nickname = nicknameUpdateRequest.getNickname();

        Authentication oldAuth = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) oldAuth.getPrincipal();

        customUser.updateNickname(nickname);
        profileService.updateNickname(customUser.getId(), nickname);
        sessionService.updateSession(request, response);

        return "redirect:/profile";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorCode", e.getBindingResult().getFieldError().getCode());
        redirectAttributes.addFlashAttribute("errorField", e.getBindingResult().getFieldError().getField());
        return "redirect:/profile?error=true";
    }

}
