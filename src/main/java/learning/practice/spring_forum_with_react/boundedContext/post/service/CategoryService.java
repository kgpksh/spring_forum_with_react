package learning.practice.spring_forum_with_react.boundedContext.post.service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import learning.practice.spring_forum_with_react.boundedContext.post.entity.Category;
import learning.practice.spring_forum_with_react.boundedContext.post.repository.CategoryRepository;
import learning.practice.spring_forum_with_react.boundedContext.post.validation.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.QueryParameterException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService implements ConstraintValidator<CategoryRequest, Integer > {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    public long selectCategoryId(String categoryName) {
        try {
            return createCategoryIdPairs().get(categoryName);
        } catch (Exception exception) {
            throw new QueryParameterException("존재 하지 않는 카테고리입니다");
        }
    }

    @Transactional(readOnly = true)
    @Cacheable("categoryId")
    public Map<String, Long> createCategoryIdPairs() {
        Map<String, Long> result = new HashMap<>();
        for (Category category : getCategoryList()) {
            result.put(category.getCategory(), category.getId());
        }

        return result;
    }

    @Transactional(readOnly = true)
    public String convertIdToCategory(long id) {
        return getCategoryList().get((int)(id - 1)).getCategory();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        try {
            getCategoryList().get(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
