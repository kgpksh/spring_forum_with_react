package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.boundedContext.post.entity.Category;
import learning.practice.spring_forum_with_react.boundedContext.post.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private List<Category> categoryList;
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryList = categoryRepository.findAll();
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }
}
