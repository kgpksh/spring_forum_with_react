package learning.practice.spring_forum_with_react.boundedContext.post.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostReadingList {
    private List<SinglePostInList> posts;
    private long oldestId;
}
