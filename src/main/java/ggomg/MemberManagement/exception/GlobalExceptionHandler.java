package ggomg.MemberManagement.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RegistrationException.class)
    public String handleRegistrationException(RegistrationException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorCode", e.getErrorCode().getCode());
        return "redirect:/register/local?error=true";
    }

    @ExceptionHandler(AuthorityException.class)
    public ResponseEntity<?> handleAuthorityException(AuthorityException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
