package learning.practice.spring_forum_with_react;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class FieldSetTest {
    @Getter
    private class FieldSetTargetClass {
        private int testInt;
        private String testString;
    }
    @Test
    void fieldSetTest() throws IllegalAccessException{
        FieldSetTargetClass target = new FieldSetTargetClass();
        TestUtils.setFieldValue(target, 3, "test");

        assertThat(target.getTestInt()).isEqualTo(3);
        assertThat(target.getTestString()).isEqualTo("test");

        assertThat(target.getTestInt()).isNotEqualTo(1);
        assertThat(target.testString).isNotEqualTo("a");
    }
}
