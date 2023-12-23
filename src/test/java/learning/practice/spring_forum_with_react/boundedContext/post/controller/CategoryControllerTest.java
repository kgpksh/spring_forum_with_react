package learning.practice.spring_forum_with_react.boundedContext.post.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import learning.practice.spring_forum_with_react.boundedContext.post.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CategoryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    void getCategoryListTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/category/categoryList"));

        MvcResult result = resultActions
                .andExpect(handler().handlerType(CategoryController.class))
                .andExpect(handler().methodName("getCategoryList"))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        List<Category> categories = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Category>>() {});

        assertThat(categories.get(0).getCategory()).isEqualTo("Cook");
    }
}