package learning.practice.spring_forum_with_react.boundedContext.member.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import learning.practice.spring_forum_with_react.boundedContext.member.dto.SignupDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {
    private SignupDto mockSignupDto(String name, String pw) throws Exception {
        SignupDto signupDto = new SignupDto();
        Class cls = Class.forName("learning.practice.spring_forum_with_react.boundedContext.member.dto.SignupDto");
        Field username = cls.getDeclaredField("username");
        Field password = cls.getDeclaredField("password");
        username.setAccessible(true);
        password.setAccessible(true);

        username.set(signupDto, name);
        password.set(signupDto, pw);

        return signupDto;
    }
    @Nested
    @MockBean(JpaMetamodelMappingContext.class)

    class SignupDtoTest {
        @Autowired
        private Validator validator;
        private List<String> validationResult(SignupDto signupDto) {
            List<String> testResult = new ArrayList<>();
            Set<ConstraintViolation<SignupDto>> validations = validator.validate(signupDto);
            for (ConstraintViolation<SignupDto> validationResult : validations) {
                testResult.add(validationResult.getMessage());
            }

            return testResult;
        }
        @Test
        @DisplayName("SignupDto 유효성 검사 테스트 username, password 모두 성공")
        void SuccessTest() throws Exception {
            SignupDto signupDto = mockSignupDto("abcdef", "1234565");
            List<String> testResult = validationResult(signupDto);
            assertThat(testResult).isEmpty();
        }

        @Test
        @DisplayName("SignupDto 유효성 검사 테스트 username, password 모두 짧아서 실패")
        void allShortFailTest() throws Exception {
            SignupDto signupDto = mockSignupDto("abc", "123");
            List<String> testResult = validationResult(signupDto);
            assertThat(testResult).contains("아이디의 길이는 5자리에서 10자리 사이만 허용 됩니다", "비밀번호의 길이는 5자리에서 10자리 사이만 허용 됩니다");
            assertThat(testResult.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("SignupDto 유효성 검사 테스트 username, password 모두 짧아서 실패")
        void allLongFailTest() throws Exception {
            SignupDto signupDto = mockSignupDto("abcsdfgdsfgdsfg", "123fgdhfdghdfghfdgh");
            List<String> testResult = validationResult(signupDto);
            assertThat(testResult).contains("아이디의 길이는 5자리에서 10자리 사이만 허용 됩니다", "비밀번호의 길이는 5자리에서 10자리 사이만 허용 됩니다");
            assertThat(testResult.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("빈 username 실패")
        void notEmptyUsernameTest() throws Exception {
            SignupDto signupDto = mockSignupDto("", "12356");
            List<String> testResult = validationResult(signupDto);
            assertThat(testResult).contains("아이디 입력은 필수입니다");
        }

        @Test
        @DisplayName("빈 password 실패")
        void notEmptyPasswordTest() throws Exception {
            SignupDto signupDto = mockSignupDto("asdfdsfg", "");
            List<String> testResult = validationResult(signupDto);
            assertThat(testResult).contains("비밀번호 입력은 필수 입니다");
        }
    }

    @Nested
    @Transactional
    class ServiceTest {
        @Autowired
        private MemberService memberService;
        @Test
        @DisplayName("회원가입 성공후 반환 확인")
        void signupSuccessReturnTest() throws Exception {
            SignupDto signupDto = mockSignupDto("asdfdsfg", "asdfsadf");
            assertThat(memberService.signUpMember(signupDto).isSuccess()).isTrue();
        }

        @Test
        @DisplayName("이미 있는 아이디로 회원가입 실패 후 반환 확인")
        void signupFailReturnTest() throws Exception {
            SignupDto signupDto = mockSignupDto("asdfdsfg", "asdf");
            memberService.signUpMember(signupDto);
            assertThat(memberService.signUpMember(signupDto).isSuccess()).isFalse();
        }

    }
}

