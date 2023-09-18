package ggomg.MemberManagement.domain.profile;

import ggomg.MemberManagement.security.LocalUser.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
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

    private final ProfileService profileService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping({"", "update"})
    public String myPage() {
        return "page/profile";
    }

    @PostMapping("nickname/update")
    public String updateNickname(@Valid NicknameUpdateRequest nicknameUpdateRequest) {

        profileService.updateNickname(
                nicknameUpdateRequest.getTargetId(),
                nicknameUpdateRequest.getNickname()
        );

        UserDetails reloadedUser = customUserDetailsService.loadUserByUserId(nicknameUpdateRequest.getTargetId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(reloadedUser, auth.getCredentials(),
                auth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);

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
