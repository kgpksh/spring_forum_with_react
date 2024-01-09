package learning.practice.spring_forum_with_react.base.rq;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;


@Component
@RequestScope
public class Rq {
    private String username;
    public Rq(HttpServletRequest request) {
        this.username = request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    public boolean isOwner(String username) {
        return this.username.equals(username);
    }
}
