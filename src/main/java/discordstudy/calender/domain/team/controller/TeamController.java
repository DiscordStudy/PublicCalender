package discordstudy.calender.domain.team.controller;

import discordstudy.calender.domain.team.dto.TeamRequest;
import discordstudy.calender.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {
    @PostMapping
    public ResponseEntity<ApiResponse<Integer>> createTeam(@RequestBody TeamRequest request, Authentication authentication) {
        String username = authentication.getName();

        return ApiResponse.okWithMessage("success", 1);
    }

}
