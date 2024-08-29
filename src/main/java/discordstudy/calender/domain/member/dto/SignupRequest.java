package discordstudy.calender.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String loginId;
    private String nickname;
    private String password;
}
