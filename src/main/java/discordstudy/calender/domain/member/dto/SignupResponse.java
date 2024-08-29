package discordstudy.calender.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private String loginId;
    private String nickname;

    public SignupResponse(String loginId, String nickname) {
        this.loginId = loginId;
        this.nickname = nickname;
    }
}
