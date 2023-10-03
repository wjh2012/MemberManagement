package ggomg.MemberManagement.domain.login_register;

import ggomg.MemberManagement.domain.member.MemberService;
import ggomg.MemberManagement.exception.RegistrationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final MemberService memberService;

    @GetMapping("/local")
    public String showRegisterForm() {
        return "page/register";
    }

    @PostMapping("/local")
    public String register(@Valid LocalMemberRegisterRequest localMemberRegisterRequest) {
        memberService.joinLocalMember(localMemberRegisterRequest);
        return "redirect:/";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorCode", e.getBindingResult().getFieldError().getCode());
        redirectAttributes.addFlashAttribute("errorField", e.getBindingResult().getFieldError().getField());
        return "redirect:/register/local?error=true";
    }

    @ExceptionHandler(RegistrationException.class)
    public String handleRegistrationException(RegistrationException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorCode", e.getErrorCode().getCode());
        return "redirect:/register/local?error=true";
    }
}
