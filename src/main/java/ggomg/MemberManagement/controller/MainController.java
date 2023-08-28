package ggomg.MemberManagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {

    @GetMapping({"", "/"})
    public String home() {
        return "home";
    }

    @GetMapping("/myPage")
    public String myPage() {
        return "page/member-info";
    }

    @GetMapping("/normal")
    public String normal() {
        return "page/normal";
    }

}
