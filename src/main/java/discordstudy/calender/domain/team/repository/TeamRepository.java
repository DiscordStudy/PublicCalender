package discordstudy.calender.domain.team.repository;

import discordstudy.calender.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<Team, Long> {

}
