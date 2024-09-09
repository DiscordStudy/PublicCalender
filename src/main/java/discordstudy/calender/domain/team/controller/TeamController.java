package discordstudy.calender.domain.team.controller;

import discordstudy.calender.domain.team.dto.TeamRequest;
import discordstudy.calender.domain.team.entity.Team;
import discordstudy.calender.domain.team.service.TeamService;
import discordstudy.calender.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createTeam(@RequestBody TeamRequest request) {
        String teamName = request.getTeamName();

        Team team=teamService.createTeam(teamName);

        return ApiResponse.okWithMessage("success",team.getId());
    }

}
