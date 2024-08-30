package discordstudy.calender.domain.member.repository;

import discordstudy.calender.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}
