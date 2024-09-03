package discordstudy.calender.domain.member.repository;

import discordstudy.calender.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByLoginId(String loginId);
}
