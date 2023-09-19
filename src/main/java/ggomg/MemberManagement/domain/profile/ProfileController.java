package ggomg.MemberManagement.domain.profile;

import ggomg.MemberManagement.domain.member.DTO.MemberResponse;
import ggomg.MemberManagement.domain.member.Member;
import ggomg.MemberManagement.domain.member.MemberService;
import ggomg.MemberManagement.security.CustomUser;
import ggomg.MemberManagement.security.LocalUser.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/profile")
public class ProfileController {

    private final MemberService memberService;
    private final ProfileService profileService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping({"", "update"})
    public String myPage(Model model) {

        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = memberService.findById(customUser.getId());
        MemberResponse memberResponse = MemberResponse.mappedByMember(member);
        model.addAttribute("member", memberResponse);

        return "page/profile";
    }

    @PostMapping("nickname/update")
    public String updateNickname(@Valid NicknameUpdateRequest nicknameUpdateRequest, Model model) {

        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        profileService.updateNickname(
                customUser.getId(),
                nicknameUpdateRequest.getNickname()
        );

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(customUser, auth.getCredentials(),
                auth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        Member member = memberService.findById(customUser.getId());
        MemberResponse memberResponse = MemberResponse.mappedByMember(member);
        model.addAttribute("member", memberResponse);

        return "page/profile";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorCode", e.getBindingResult().getFieldError().getCode());
        redirectAttributes.addFlashAttribute("errorField", e.getBindingResult().getFieldError().getField());
        return "redirect:/profile?error=true";
    }


}
