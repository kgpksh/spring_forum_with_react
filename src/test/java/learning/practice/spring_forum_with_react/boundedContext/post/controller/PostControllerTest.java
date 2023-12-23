package learning.practice.spring_forum_with_react.boundedContext.post.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import learning.practice.spring_forum_with_react.TestUtils;
import learning.practice.spring_forum_with_react.boundedContext.member.dto.LoginForm;
import learning.practice.spring_forum_with_react.boundedContext.member.dto.SignupDto;
import learning.practice.spring_forum_with_react.boundedContext.member.service.MemberService;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostList;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostSaveForm;
import org.hibernate.QueryParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;
    private String testJwt;
    @Autowired
    private Validator validator;

    @BeforeEach
    private void createTestJwt() throws IllegalAccessException{
        if (testJwt != null) {
            return;
        }

        SignupDto signupDto = new SignupDto();
        TestUtils.setFieldValue(signupDto, "testUser", "12345");
        memberService.signUpMember(signupDto);

        LoginForm loginForm = new LoginForm();
        TestUtils.setFieldValue(loginForm, "testUser", "12345");

        testJwt = memberService.login(loginForm).getData();
    }

    private String requestExpectOk(String url, MultiValueMap getMethodParams, String methodName) throws Exception {
        ResultActions resultActions = mockMvc.perform(get(url).params(getMethodParams));

        MvcResult result = resultActions
                .andExpect(handler().handlerType(PostController.class))
                .andExpect(handler().methodName(methodName))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    @Test
    void postPageListTest() throws Exception {
        MultiValueMap<String, String> getMethodParams = new LinkedMultiValueMap<>();
        getMethodParams.add("category", "Book");
        getMethodParams.add("oldestId", "2401");
        String responseContent = requestExpectOk("/post/list", getMethodParams, "showPostPage");

        ObjectMapper mapper = new ObjectMapper();
        List<PostList> postList = mapper.readValue(responseContent, new TypeReference<List<PostList>>() {});

        assertThat(postList.get(0).getTitle()).isEqualTo("title2397");
        assertThat(postList.get(postList.size() - 1).getId()).isEqualTo(401L);
    }

    @Test
    void postPageCategoryFailTest() throws Exception {
        MultiValueMap<String, String> getMethodParams = new LinkedMultiValueMap<>();
        getMethodParams.add("category", "aba");
        getMethodParams.add("oldestId", "2401");

        assertThatThrownBy(() -> requestExpectOk("/post/list", getMethodParams, "showPostPage"))
                .isInstanceOf(ServletException.class)
                .hasCauseInstanceOf(QueryParameterException.class);
    }

    @Test
    void readPostListWithoutCategory() throws Exception {
        MultiValueMap<String, String> getMethodParams = new LinkedMultiValueMap<>();
        getMethodParams.add("oldestId", "2000");
        String responseContent = requestExpectOk("/post/list", getMethodParams, "showPostPage");

        ObjectMapper mapper = new ObjectMapper();
        List<PostList> postList = mapper.readValue(responseContent, new TypeReference<List<PostList>>() {});

        assertThat(postList.get(0).getTitle()).isEqualTo("title1999");
        assertThat(postList.get(postList.size() - 1).getId()).isEqualTo(1500L);
    }

    @Test
    void readPostListUnderPageUnitLength() throws Exception {
        MultiValueMap<String, String> getMethodParams = new LinkedMultiValueMap<>();
        getMethodParams.add("oldestId", "2");
        String responseContent = requestExpectOk("/post/list", getMethodParams, "showPostPage");

        ObjectMapper mapper = new ObjectMapper();
        List<PostList> postList = mapper.readValue(responseContent, new TypeReference<List<PostList>>() {});

        assertThat(postList.get(0).getTitle()).isEqualTo("title1");
        assertThat(postList.get(postList.size() - 1).getId()).isEqualTo(1L);
    }

    @Test
    void postUpdateSuccessTest() throws Exception {
        PostSaveForm postSaveForm = new PostSaveForm();
        TestUtils.setFieldValue(postSaveForm, "testAuthor", 1, "testTitle", "testContent");

        ObjectMapper mapper = new ObjectMapper();
        ResultActions resultActions = mockMvc.perform(
                post("/post/post")
                        .header("Authorization", testJwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(postSaveForm))
            )
                .andExpect(status().isOk());
    }

    @Test
    void postUpdateAuthFormatFailTest() throws Exception {
        PostSaveForm postSaveForm = new PostSaveForm();
        TestUtils.setFieldValue(postSaveForm, "testAuthor", 1, "testTitle", "testContent");

        ObjectMapper mapper = new ObjectMapper();
        assertThatThrownBy(() ->
                    mockMvc.perform(
                            post("/post/post")
                                    .header("Authorization", "asdf")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(mapper.writeValueAsString(postSaveForm))
                    )
                )
                .isInstanceOf(MalformedJwtException.class);
    }

    @Test
    void postUpdateAuthExpriedFailTest() throws Exception {
        PostSaveForm postSaveForm = new PostSaveForm();
        TestUtils.setFieldValue(postSaveForm, "testAuthor", 1, "testTitle", "testContent");
        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6InRlc3RVc2VyIiwiZXhwIjoxNjAzMzU1NzQ2fQ.0UXCEu9kLrFoHKJBvC_w6MNMaUWHbNAiw7yq4D8QBgUp-tiObAQQd5D1a4lu7grJu4M4GVJ1khJY_gr5GcK1aw";

        ObjectMapper mapper = new ObjectMapper();
        assertThatThrownBy(() ->
                mockMvc.perform(
                        post("/post/post")
                                .header("Authorization", expiredToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(postSaveForm))
                )
                        .andExpect(status().isUnauthorized())
        )
                .isInstanceOf(SignatureException.class);
    }

    private List<String> validationResult(Object form) {
        List<String> testResult = new ArrayList<>();
        Set<ConstraintViolation<Object>> validations = validator.validate(form);
        for (ConstraintViolation<Object> validationResult : validations) {
            testResult.add(validationResult.getMessage());
        }

        return testResult;
    }

    @Test
    void postUpdateWrongAuthorTest() throws Exception {
        PostSaveForm postSaveForm = new PostSaveForm();
        TestUtils.setFieldValue(postSaveForm, "", 1, "testTitle", "testContent");
        List<String> validationResult = validationResult(postSaveForm);

        assertThat(validationResult).contains("공백일 수 없습니다");
    }

    @Test
    void postUpdateWrongTitleTest() throws Exception {
        PostSaveForm postSaveForm = new PostSaveForm();
        TestUtils.setFieldValue(postSaveForm, "testAuthor", 1, "", "testContent");
        List<String> validationResult = validationResult(postSaveForm);

        assertThat(validationResult).contains("공백일 수 없습니다");
    }

    @Test
    void postUpdateCorrectContentTest() throws Exception {
        PostSaveForm postSaveForm = new PostSaveForm();
        TestUtils.setFieldValue(postSaveForm, "testAuthor", 1, "testTitle", "");
        List<String> validationResult = validationResult(postSaveForm);
    }

    @Test
    void postUpdateWrongContentTest() throws Exception {
        PostSaveForm postSaveForm = new PostSaveForm();
        TestUtils.setFieldValue(postSaveForm, "testAuthor", 1, "testTitle", null);
        List<String> validationResult = validationResult(postSaveForm);

        assertThat(validationResult).contains("널이어서는 안됩니다");
    }

    @Test
    void postUpdateWrongCategoryTest() throws Exception {
        PostSaveForm postSaveForm = new PostSaveForm();
        TestUtils.setFieldValue(postSaveForm, "testAuthor", 100000, "testTitle", "testContent");
        List<String> validationResult = validationResult(postSaveForm);

        assertThat(validationResult).contains("에러");
    }
}