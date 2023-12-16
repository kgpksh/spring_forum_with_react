package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.boundedContext.post.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {
    @Autowired
    CategoryService categoryService;

    @Test
    void currentCategoriesCheckTest() {
        List<Category> categoryList =categoryService.getCategoryList();
        assertThat(categoryList.get(0).getId()).isEqualTo(1L);
        assertThat(categoryList.get(0).getCategory()).isEqualTo("Cook");
    }

    @Test
    void selectCategoryIdTest() {
        long categoryId = categoryService.selectCategoryId("Cook");
        assertThat(categoryId).isEqualTo(1L);
    }

    @Test
    void convertIdToCategoryTest() {
        String category = categoryService.convertIdToCategory(1L);
        assertThat(category).isEqualTo("Cook");
    }
}