package learning.practice.spring_forum_with_react.base.resData;

import learning.practice.spring_forum_with_react.boundedContext.member.dto.SignupDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ResponseDataWrapperTest {
    private TestClass testClass= new TestClass();
    private class TestClass {
        private String username = "username";
        private String password = "password";
    }

    @Test
    @DisplayName("ResponseWrapper 성공")
    void responseWrapperSuccessTest() {
        ResponseDataWrapper<TestClass> testWrapper = ResponseDataWrapper.of("S-123", "test", testClass);
        assertThat(testWrapper.isSuccess()).isTrue();
        assertThat(testWrapper.isFailed()).isFalse();
    }

    @Test
    @DisplayName("ResponseWrapper 실패")
    void responseWrapperFailTest() {
        ResponseDataWrapper<TestClass> testWrapper = ResponseDataWrapper.of("A-123", "test", testClass);
        assertThat(testWrapper.isSuccess()).isFalse();
        assertThat(testWrapper.isFailed()).isTrue();
    }

    @Test
    @DisplayName("ResponseWrapper 데이터 제외")
    void responseWrapperTest() {
        ResponseDataWrapper<SignupDto> testWrapper = ResponseDataWrapper.of("A-123", "test");
        assertThat(testWrapper.getData()).isNull();
    }

    @Test
    @DisplayName("validation 결과물 성공")
    void afterSuccesValidation() {
        ResponseDataWrapper<SignupDto> testWrapper = ResponseDataWrapper.validate("testCode", true);
        assertThat(testWrapper.isSuccess()).isTrue();
    }

    @Test
    @DisplayName("validation 결과물 실패")
    void afterFailValidation() {
        ResponseDataWrapper<SignupDto> testWrapper = ResponseDataWrapper.validate("testCode", false);
        assertThat(testWrapper.isSuccess()).isFalse();
    }
}