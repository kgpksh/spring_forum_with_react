package learning.practice.spring_forum_with_react.boundedContext.member.repository;

import learning.practice.spring_forum_with_react.boundedContext.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByUsername(String username);
}
