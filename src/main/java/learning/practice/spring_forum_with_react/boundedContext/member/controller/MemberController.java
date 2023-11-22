package learning.practice.spring_forum_with_react.boundedContext.member.controller;

import jakarta.validation.Valid;
import learning.practice.spring_forum_with_react.boundedContext.member.dto.SignupDto;
import learning.practice.spring_forum_with_react.boundedContext.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(@Valid SignupDto signupDto) {
        return memberService.signUpMember(signupDto).getMsg();
    }
}
