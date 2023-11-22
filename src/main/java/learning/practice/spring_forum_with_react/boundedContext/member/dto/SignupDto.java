package learning.practice.spring_forum_with_react.boundedContext.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupDto {
    @NotNull(message = "아이디 입력은 필수입니다")
    @NotBlank(message = "아이디 입력은 필수입니다")
    @Size(min = 5, max = 10, message = "아이디의 길이는 5자리에서 10자리 사이만 허용 됩니다")
    private String username;

    @NotNull(message = "비밀번호 입력은 필수 입니다")
    @NotBlank(message = "비밀번호 입력은 필수 입니다")
    @Size(min = 5, max = 10, message = "비밀번호의 길이는 5자리에서 10자리 사이만 허용 됩니다")
    private String password;
}
