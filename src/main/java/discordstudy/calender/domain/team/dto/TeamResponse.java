package discordstudy.calender.domain.team.dto;

import lombok.Getter;

@Getter
public class TeamResponse {
    private Long teamId;

    public TeamResponse(Long teamId) {
        this.teamId = teamId;
    }
}
