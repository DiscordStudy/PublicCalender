package discordstudy.calender.domain.team.service;

import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.domain.member.repository.MemberRepository;
import discordstudy.calender.domain.team.entity.Team;
import discordstudy.calender.domain.team.repository.TeamRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createTeam(String teamName) {
        Member member=new Member();
        Team team = new Team(teamName,member);//팀 생성 및 멤버 연결
        member.addTeam(team); // 양방향 관계 설정
        // 팀 저장
        teamRepository.save(team);
        return team.getId();
    }


}
