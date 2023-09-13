package ggomg.MemberManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {

    @GetMapping({"", "/"})
    public String home() {
        return "home";
    }

    @GetMapping("/myPage")
    public String myPage(Model model) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        log.info("" + user);
        model.addAttribute("info", user);
        return "page/member-info";
    }

    @GetMapping("/normal")
    public String normal() {
        return "page/normal";
    }

}
