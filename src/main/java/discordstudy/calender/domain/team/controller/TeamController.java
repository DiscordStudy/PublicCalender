package discordstudy.calender.domain.team.controller;

import discordstudy.calender.domain.team.dto.TeamRequest;
import discordstudy.calender.domain.team.dto.TeamResponse;
import discordstudy.calender.domain.team.service.TeamService;
import discordstudy.calender.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<ApiResponse<TeamResponse>> createTeam(@RequestBody TeamRequest request, Authentication authentication) {
        String teamName = request.getTeamName();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long teamId = teamService.createTeam(teamName);//Service에서 team을 만들면 teamid만 반환
        TeamResponse response = new TeamResponse(teamId);
        //skype라는 팀을 만들었는데 거기에 사람을 넣어줘야하는 부분 추가 !
        return ApiResponse.okWithMessage("success", response);
    }

}
