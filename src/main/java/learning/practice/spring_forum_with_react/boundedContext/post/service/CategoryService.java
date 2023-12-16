package learning.practice.spring_forum_with_react.boundedContext.post.service;

import learning.practice.spring_forum_with_react.boundedContext.post.entity.Category;
import learning.practice.spring_forum_with_react.boundedContext.post.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    private List<Category> categoryList;
    private CategoryRepository categoryRepository;
    private Map<String, Long> categoryIds = new HashMap<>();

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryList = categoryRepository.findAll();
        for (Category category : categoryList) {
            this.categoryIds.put(category.getCategory(), category.getId());
        }
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }
    public long selectCategoryId(String category) {
        return categoryIds.get(category);
    }

    public String convertIdToCategory(long id) {
        return categoryList.get((int)(id - 1)).getCategory();
    }
}
