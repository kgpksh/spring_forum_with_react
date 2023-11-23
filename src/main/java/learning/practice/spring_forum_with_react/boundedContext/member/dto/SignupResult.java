package learning.practice.spring_forum_with_react.boundedContext.member.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupResult {
    private boolean successSignup;
    private String resultMsg;
}
