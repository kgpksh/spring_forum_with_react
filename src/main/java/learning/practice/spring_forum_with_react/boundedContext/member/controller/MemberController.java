package learning.practice.spring_forum_with_react.boundedContext.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/test")
    public String testMemberPage() {
        return "Hello React!";
    }
}
