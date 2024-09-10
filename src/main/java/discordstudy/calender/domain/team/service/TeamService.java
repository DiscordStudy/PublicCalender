package discordstudy.calender.domain.team.service;

import discordstudy.calender.domain.team.entity.Team;
import discordstudy.calender.domain.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    @Transactional
    public Long createTeam(String teamName) {
        Team team = new Team();
        team.setTeamName(teamName);
        teamRepository.save(team);
        return team.getId();
    }


}
