package ggomg.MemberManagement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    @GetMapping({"", "/"})
    public String home() {
        return "home";
    }

    @GetMapping("/normal")
    public String normal() {

        return "page/normal";

    }

}
