package ggomg.MemberManagement.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorField", e.getBindingResult().getFieldError().getCode());
        redirectAttributes.addFlashAttribute("errorField", e.getBindingResult().getFieldError().getField());
        return "redirect:/register/local?error=true";
    }

    @ExceptionHandler(RegistrationException.class)
    public String handleRegistrationException(RegistrationException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorCode", e.getErrorCode().getCode());
        return "redirect:/register/local?error=true";
    }

}
