package ggomg.MemberManagement.API.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/api")
public class APIController {

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok().body("success");
    }

}
