package learning.practice.spring_forum_with_react.boundedContext.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResult {
    private boolean successLogin;
    private String resultMsg;
    private String token;
}
