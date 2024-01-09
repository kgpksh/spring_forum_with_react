package learning.practice.spring_forum_with_react.boundedContext.member.entity;

import jakarta.persistence.*;
import learning.practice.spring_forum_with_react.base.baseEntity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Where(clause = "deleted_date is null")
@SQLDelete(sql = "UPDATE member set deleted_date = NOW() where id = ?")
public class Member extends BaseEntity {
    private String username;
    private String password;

    private boolean isAdmin;
    private String providerTypeCode;

    public List<? extends GrantedAuthority> takeGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));

        if (isAdmin) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return grantedAuthorities;
    }
}
