package discordstudy.calender.domain.team.service;

import discordstudy.calender.domain.team.entity.Team;
import discordstudy.calender.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public Team createTeam(String teamName)
    {
        Team team=new Team();
        team.setTeamName(teamName);
        return teamRepository.save(team);
    }

}
