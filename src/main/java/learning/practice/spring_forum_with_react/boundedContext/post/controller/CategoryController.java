package learning.practice.spring_forum_with_react.boundedContext.post.controller;

import learning.practice.spring_forum_with_react.boundedContext.post.entity.Category;
import learning.practice.spring_forum_with_react.boundedContext.post.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categoryList")
    public List<Category> getCategoryList() {

        return categoryService.getCategoryList();
    }
}
