package learning.practice.spring_forum_with_react.boundedContext.member.controller;

import jakarta.validation.Valid;
import learning.practice.spring_forum_with_react.base.resData.ResponseDataWrapper;
import learning.practice.spring_forum_with_react.boundedContext.member.dto.LoginForm;
import learning.practice.spring_forum_with_react.boundedContext.member.dto.LoginResult;
import learning.practice.spring_forum_with_react.boundedContext.member.dto.SignupDto;
import learning.practice.spring_forum_with_react.boundedContext.member.dto.SignupResult;
import learning.practice.spring_forum_with_react.boundedContext.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResult> signup(@RequestBody @Valid SignupDto signupDto) {
        ResponseDataWrapper result = memberService.signUpMember(signupDto);

        SignupResult signupResult = new SignupResult();
        signupResult.setSuccessSignup(result.isSuccess());
        signupResult.setResultMsg(result.getMsg());
        return ResponseEntity.ok(signupResult);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResult> login(@RequestBody @Valid LoginForm loginForm) {
        ResponseDataWrapper<String> result = memberService.login(loginForm);

        LoginResult loginResult = new LoginResult();
        loginResult.setSuccessLogin(result.isSuccess());
        loginResult.setResultMsg(result.getMsg());
        loginResult.setToken(result.getData());

        return ResponseEntity.ok(loginResult);
    }
}
