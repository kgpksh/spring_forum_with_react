package learning.practice.spring_forum_with_react.boundedContext.member.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginForm {
    @NotNull
    private String username;

    @NotNull
    private String password;
}
