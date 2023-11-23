package learning.practice.spring_forum_with_react.boundedContext.member.service;

import learning.practice.spring_forum_with_react.base.resData.ResponseDataWrapper;
import learning.practice.spring_forum_with_react.boundedContext.member.dto.SignupDto;
import learning.practice.spring_forum_with_react.boundedContext.member.entity.Member;
import learning.practice.spring_forum_with_react.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private static final String SIGNUP_FAIL_ALREADY_EXISTS = "이미 존재하는 아이디입니다";
    private static final String SIGNUP_SUCCESS = "회원 가입 성공";
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseDataWrapper signUpMember(SignupDto signupDto) {
        ResponseDataWrapper validation = validateSignUp(signupDto);
        if (validation.isSuccess()) {
            Member member = new Member();
            member.setAdmin(false);
            member.setUsername(signupDto.getUsername());
            member.setPassword(passwordEncoder.encode(signupDto.getPassword()));
            member.setProviderTypeCode("DIRECT_SIGNUP");
            memberRepository.save(member);
        }

        return validation;
    }

    private ResponseDataWrapper validateSignUp(SignupDto signupDto) {
        Optional<Member> wrappedMember = memberRepository.findMemberByUsername(signupDto.getUsername());
        if (wrappedMember.isEmpty()) {
            return ResponseDataWrapper.validate("1", SIGNUP_SUCCESS, true);
        }
        return ResponseDataWrapper.validate("1", SIGNUP_FAIL_ALREADY_EXISTS, false);
    }
}
