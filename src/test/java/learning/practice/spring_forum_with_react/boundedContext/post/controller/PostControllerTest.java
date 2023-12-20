package learning.practice.spring_forum_with_react.boundedContext.post.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import learning.practice.spring_forum_with_react.boundedContext.post.dto.PostList;
import org.hibernate.QueryParameterException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    private PostController postController;
    @Autowired
    private MockMvc mockMvc;

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
}